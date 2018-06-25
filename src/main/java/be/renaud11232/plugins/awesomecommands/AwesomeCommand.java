package be.renaud11232.plugins.awesomecommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.*;

public class AwesomeCommand extends Command {

    public static final CommandExecutor DEFAULT_EXECUTOR = (commandSender, command, s, strings) -> false;
    public static final CommandExecutor NO_EXECUTOR = (commandSender, command, s, strings) -> true;

    public static final TabCompleter DEFAULT_COMPLETER = (commandSender, command, s, strings) -> null;
    public static final TabCompleter NO_COMPLETER = (commandSender, command, s, strings) -> Collections.emptyList();

    private Map<String, AwesomeCommand> subCommands;
    private CommandExecutor executor;
    private TabCompleter tabCompleter;

    public AwesomeCommand(String name) {
        super(name);
        subCommands = new HashMap<>();
        executor = DEFAULT_EXECUTOR;
        tabCompleter = DEFAULT_COMPLETER;
        usageMessage = "/<command>";
    }

    public CommandExecutor getExecutor() {
        return executor;
    }

    public void setExecutor(CommandExecutor executor) {
        this.executor = executor;
    }

    public TabCompleter getTabCompleter() {
        return tabCompleter;
    }

    public void setTabCompleter(TabCompleter tabCompleter) {
        this.tabCompleter = tabCompleter;
    }

    public AwesomeCommand getSubCommand(String name) {
        var nameSplit = name.split(".", 2);
        return nameSplit.length == 1 ? subCommands.get(nameSplit[0]) : subCommands.get(nameSplit[0]).getSubCommand(nameSplit[1]);
    }

    public void addSubCommand(AwesomeCommand awesomeCommand) {
        subCommands.put(awesomeCommand.getName(), awesomeCommand);
        awesomeCommand.getAliases()
                .stream()
                .filter(alias -> !subCommands.containsKey(alias))
                .forEach(alias -> subCommands.put(alias, awesomeCommand));
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if(testPermission(commandSender)) {
            if(strings.length == 0 || !subCommands.containsKey(strings[0])) {
                if(!executor.onCommand(commandSender, this, s, strings)){
                    if(getUsage().length() > 0) {
                        Arrays.asList(getUsage().replace("<command>", s).split("(\r\n|\n)"))
                                .forEach(commandSender::sendMessage);
                    }
                }
            } else {
                subCommands.get(strings[0]).execute(commandSender, s + " " + strings[0], Arrays.copyOfRange(strings, 1, strings.length));
            }
        }
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender commandSender, String s, String[] strings) {
        if(testPermissionSilent(commandSender)) {
            return null;
        } else {
            return NO_COMPLETER.onTabComplete(commandSender, this, s, strings);
        }
    }
}
