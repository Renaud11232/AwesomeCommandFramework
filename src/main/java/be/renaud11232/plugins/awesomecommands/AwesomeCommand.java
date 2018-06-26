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

    private AwesomeCommandMap subCommands;
    private CommandExecutor executor;
    private TabCompleter tabCompleter;

    public AwesomeCommand(String name) {
        super(name);
        subCommands = new AwesomeCommandMap();
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
        return subCommands.getCommand(name);
    }

    public void addSubCommand(AwesomeCommand awesomeCommand) {
        subCommands.putCommand(awesomeCommand);
    }

    public void removeSubCommand(AwesomeCommand awesomeCommand) {
        subCommands.removeCommand(awesomeCommand);
    }

    public void clearSubCommands() {
        subCommands.clearCommands();
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (testPermission(commandSender)) {
            if (strings.length == 0 || !subCommands.hasCommand(strings[0])) {
                if (!executor.onCommand(commandSender, this, s, strings) && getUsage().length() > 0) {
                    Arrays.asList(getUsage().replace("<command>", s).split("(\r\n|\n)"))
                            .forEach(commandSender::sendMessage);
                }
            } else {
                subCommands.getCommand(strings[0]).execute(commandSender, s + " " + strings[0], Arrays.copyOfRange(strings, 1, strings.length));
            }
        }
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender commandSender, String s, String[] strings) {
        if (strings.length > 0 && testPermissionSilent(commandSender)) {
            if (subCommands.hasCommand(strings[0])) {
                return subCommands.getCommand(strings[0]).tabComplete(commandSender, s + " " + strings[0], Arrays.copyOfRange(strings, 1, strings.length));
            } else {
                var completionSet = new HashSet<String>();
                if (strings.length == 1) {
                    subCommands.getKnownCommandNames()
                            .stream()
                            .filter(subcommand -> subcommand.startsWith(strings[0]))
                            .forEach(completionSet::add);
                }
                var otherCompletions = tabCompleter.onTabComplete(commandSender, this, s, strings);
                if (otherCompletions == null) {
                    completionSet.addAll(super.tabComplete(commandSender, s, strings));
                } else {
                    completionSet.addAll(otherCompletions);
                }
                var completionList = new LinkedList<>(completionSet);
                completionList.sort(String.CASE_INSENSITIVE_ORDER);
                return completionList;
            }
        } else {
            return NO_COMPLETER.onTabComplete(commandSender, this, s, strings);
        }
    }
}
