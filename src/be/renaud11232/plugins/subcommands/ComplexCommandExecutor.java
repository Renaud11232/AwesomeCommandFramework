package be.renaud11232.plugins.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.*;

/**
 * Defines a {@link CommandExecutor} which can execute a command and some {@link SubCommand}s.
 * <p>
 * A {@link ComplexCommandExecutor} works as follows :<br/>
 * <ol>
 * <li>
 * If there's no argument or the first argument does not match a {@link SubCommand} name :<br/>
 * The {@link CommandExecutor} of this {@link ComplexCommandExecutor} is executed.
 * </li>
 * <li>
 * Otherwise (If there is an argument and it matches a {@link SubCommand} name) :<br/>
 * The {@link CommandExecutor} of the matching {@link SubCommand} is executed.
 * </li>
 * </ol>
 * </p>
 */
public class ComplexCommandExecutor implements CommandExecutor {

    /**
     * {@link CommandExecutor} that works the same as the default {@link CommandExecutor}.
     * <p>
     * It does nothing and always returns <code>false</code>.
     * </p>
     */
    public static final CommandExecutor DEFAULT_EXECUTOR = new CommandExecutor() {
        @Override
        public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
            return false;
        }
    };

    /**
     * {@link CommandExecutor} that does nothing and always returns <code>true</code>.
     */
    public static final CommandExecutor NO_EXECUTOR = new CommandExecutor() {
        @Override
        public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
            return true;
        }
    };

    private Map<String, CommandExecutor> subCommands;
    private CommandExecutor executor;

    /**
     * Constructs a new {@link ComplexCommandExecutor} with DEFAULT_EXECUTOR as executor and no {@link SubCommand}s.
     */
    public ComplexCommandExecutor() {
        subCommands = new HashMap<>();
        executor = DEFAULT_EXECUTOR;
    }

    /**
     * Constructs a new {@link ComplexCommandExecutor} with a given {@link CommandExecutor} and some {@link SubCommand}s.
     *
     * @param executor    the {@link CommandExecutor} to set for this {@link ComplexCommandExecutor}.
     * @param subCommands the {@link SubCommand}s to set for this {@link ComplexCommandExecutor}.
     */
    public ComplexCommandExecutor(CommandExecutor executor, SubCommand... subCommands) {
        this();
        setExecutor(executor);
        addSubCommands(subCommands);
    }

    /**
     * Adds a {@link SubCommand} to this {@link ComplexCommandExecutor}.
     * <p>
     * If there was a {@link SubCommand} with the same name, the old {@link SubCommand} is replaced by the new one.
     * </p>
     *
     * @param subCommand the {@link SubCommand} to add.
     */
    public void addSubCommand(SubCommand subCommand) {
        subCommands.put(subCommand.getName(), subCommand.getExecutor());
        for (String alias : subCommand.getAliases()) {
            subCommands.put(alias, subCommand.getExecutor());
        }
    }

    /**
     * Adds {@link SubCommand}s to this {@link ComplexCommandExecutor}.
     * <p>
     * If there was a {@link SubCommand} with the same name or if two or more of the given {@link SubCommand}s had the same name,<br/>
     * only the last one is stored.
     * </p>
     *
     * @param subCommands the {@link SubCommand}s to add.
     */
    public void addSubCommands(SubCommand... subCommands) {
        for (SubCommand sub : subCommands) {
            addSubCommand(sub);
        }
    }

    /**
     * Sets the {@link CommandExecutor} of this {@link ComplexCommandExecutor}.
     *
     * @param executor the {@link CommandExecutor} to set for this {@link ComplexCommandExecutor}.
     */
    public void setExecutor(CommandExecutor executor) {
        this.executor = executor == null ? DEFAULT_EXECUTOR : executor;
    }

    /**
     * Executes this {@link ComplexCommandExecutor}.
     * <p>
     * <ol>
     * <li>
     * If there's no argument or the first argument does not match a {@link SubCommand} name :<br/>
     * The {@link CommandExecutor} of this {@link ComplexCommandExecutor} is executed.
     * </li>
     * <li>
     * Otherwise (If there is an argument and it matches a {@link SubCommand} name) :<br/>
     * The {@link CommandExecutor} of the matching {@link SubCommand} is executed.
     * </li>
     * </ol>
     * </p>
     * <p>
     * This means if you have a {@link SubCommand} with a given name, the {@link CommandExecutor} of this {@link ComplexCommandExecutor} will never get that given name as an argument.<br/>
     * It will always call the {@link SubCommand} executor instead.
     * </p>
     *
     * @param commandSender the {@link CommandSender}.
     * @param command       the sent {@link Command}.
     * @param s             the alias of the {@link Command}.
     * @param strings       the arguments of the {@link Command}.
     * @return <code>true</code> if the command was successful, <code>false</code> otherwise.
     */
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 0 || !subCommands.containsKey(strings[0])) {
            return executor.onCommand(commandSender, command, s, strings);
        } else {
            return subCommands.get(strings[0]).onCommand(commandSender, command, s, Arrays.copyOfRange(strings, 1, strings.length));
        }
    }
}
