package be.renaud11232.plugins.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ComplexCommandExecutor implements CommandExecutor {

    public static final CommandExecutor DEFAULT_EXECUTOR = new CommandExecutor() {
        @Override
        public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
            return false;
        }
    };
    public static final CommandExecutor NO_EXECUTOR = new CommandExecutor() {
        @Override
        public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
            return true;
        }
    };
    private Map<String, CommandExecutor> subCommands;
    private CommandExecutor executor;

    public ComplexCommandExecutor() {
        subCommands = new HashMap<>();
        executor = DEFAULT_EXECUTOR;
    }

    public ComplexCommandExecutor(CommandExecutor executor, SubCommand... subCommands) {
        this();
        setExecutor(executor);
        addSubCommands(subCommands);
    }

    public void addSubCommand(SubCommand subCommand) {
        subCommands.put(subCommand.getName(), subCommand.getExecutor());
        for (String alias : subCommand.getAliases()) {
            subCommands.put(alias, subCommand.getExecutor());
        }
    }

    public void addSubCommands(SubCommand... subCommands) {
        for (SubCommand sub : subCommands) {
            addSubCommand(sub);
        }
    }

    public void setExecutor(CommandExecutor executor) {
        this.executor = executor == null ? DEFAULT_EXECUTOR : executor;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 0 || !subCommands.containsKey(strings[0])) {
            return executor.onCommand(commandSender, command, s, strings);
        } else {
            return subCommands.get(strings[0]).onCommand(commandSender, command, s, Arrays.copyOfRange(strings, 1, strings.length));
        }
    }
}
