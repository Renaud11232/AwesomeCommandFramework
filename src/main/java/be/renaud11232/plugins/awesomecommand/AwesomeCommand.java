package be.renaud11232.plugins.awesomecommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.*;

/**
 * Defines a {@link Command} just like the regular ones but quite better
 */
public class AwesomeCommand extends Command {

    /**
     * Default {@link CommandExecutor} that always returns false.
     */
    public static final CommandExecutor DEFAULT_EXECUTOR = (commandSender, command, s, strings) -> false;
    /**
     * {@link CommandExecutor} that always returns true.
     */
    public static final CommandExecutor NO_EXECUTOR = (commandSender, command, s, strings) -> true;

    /**
     * Default {@link TabCompleter} that always returns null.
     */
    public static final TabCompleter DEFAULT_COMPLETER = (commandSender, command, s, strings) -> null;
    /**
     * {@link TabCompleter} that always returns an empty {@link List}.
     */
    public static final TabCompleter NO_COMPLETER = (commandSender, command, s, strings) -> Collections.emptyList();

    private AwesomeCommandMap subCommands;
    private CommandExecutor executor;
    private TabCompleter tabCompleter;

    /**
     * Constructs a new {@link AwesomeCommand}
     * @param name the name of the command to create
     */
    public AwesomeCommand(String name) {
        super(name);
        subCommands = new AwesomeCommandMap();
        executor = DEFAULT_EXECUTOR;
        tabCompleter = DEFAULT_COMPLETER;
        usageMessage = "/<command>";
    }

    /**
     *
     * Gets the {@link CommandExecutor} for this {@link AwesomeCommand}
     * @return the {@link CommandExecutor}
     */
    public CommandExecutor getExecutor() {
        return executor;
    }

    /**
     * Sets the {@link CommandExecutor} for this {@link AwesomeCommand}
     * @param executor the {@link CommandExecutor} to set
     */
    public void setExecutor(CommandExecutor executor) {
        this.executor = executor;
    }

    /**
     * Gets the {@link TabCompleter} for this {@link AwesomeCommand}
     * @return the {@link TabCompleter}
     */
    public TabCompleter getTabCompleter() {
        return tabCompleter;
    }

    /**
     * Sets the {@link TabCompleter} for this {@link AwesomeCommand}
     * @param tabCompleter the {@link TabCompleter} to set
     */
    public void setTabCompleter(TabCompleter tabCompleter) {
        this.tabCompleter = tabCompleter;
    }

    /**
     * Gets the sub command with the given name.
     * The given name can be in the format parent.subcommand for recursive search.
     * @param name the name of the subcommand
     * @return the {@link AwesomeCommand}
     */
    public AwesomeCommand getSubCommand(String name) {
        return subCommands.getCommand(name);
    }

    /**
     * Adds a given {@link AwesomeCommand} as a sob command of this {@link AwesomeCommand}
     * @param awesomeCommand the {@link AwesomeCommand} to add as a sub command
     */
    public void addSubCommand(AwesomeCommand awesomeCommand) {
        subCommands.putCommand(awesomeCommand);
    }

    /**
     * Removes an {@link AwesomeCommand} from the sub commands
     * @param awesomeCommand the {@link AwesomeCommand} to remove
     */
    public void removeSubCommand(AwesomeCommand awesomeCommand) {
        subCommands.removeCommand(awesomeCommand);
    }

    /**
     * Removes all this {@link AwesomeCommand}'s sub commands
     */
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
