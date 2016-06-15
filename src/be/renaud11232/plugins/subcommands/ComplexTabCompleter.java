package be.renaud11232.plugins.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.*;

/**
 * Defines a {@link TabCompleter} which can complete a command with some {@link SubTab}s.
 * <p>
 * A {@link ComplexTabCompleter} works as follows :<br/>
 * <ol>
 * <li>
 * Checks for fully matching {@link SubTab}.<br/>
 * If there is any, calls the {@link TabCompleter} of that {@link SubTab}.
 * </li>
 * <li>
 * If not,<br/>
 * Looks for completion to match the beginning of a {@link SubTab} name.
 * </li>
 * <li>
 * If no {@link SubTab} matched the beginning of the first argument,<br/>
 * Calls the {@link TabCompleter} of this {@link ComplexTabCompleter}.
 * </li>
 * </ol>
 * </p>
 */
public class ComplexTabCompleter implements TabCompleter {

    /**
     * {@link TabCompleter} that works the same as the default {@link TabCompleter}.
     * <p>
     * It does nothing and always returns <code>null</code>.
     * </p>
     */
    public static final TabCompleter DEFAULT_COMPLETER = new TabCompleter() {
        @Override
        public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
            return null;
        }
    };

    /**
     * {@link TabCompleter} that always returns an empty list.
     */
    public static final TabCompleter NO_TAB_COMPLETER = new TabCompleter() {
        @Override
        public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
            return Collections.emptyList();
        }
    };
    private Map<String, TabCompleter> subCompleters;
    private TabCompleter completer;

    /**
     * Constructs a new {@link ComplexTabCompleter} with DEFAULT_COMPLETER as completer and no {@link SubTab}s.
     */
    public ComplexTabCompleter() {
        subCompleters = new HashMap<>();
        completer = DEFAULT_COMPLETER;
    }

    /**
     * Constructs a new {@link ComplexTabCompleter} with a given {@link TabCompleter} and some {@link SubTab}s.
     *
     * @param completer the {@link TabCompleter} to set for this {@link ComplexTabCompleter}.
     * @param subTabs   the {@link SubTab}s to set for this {@link ComplexTabCompleter}.
     */
    public ComplexTabCompleter(TabCompleter completer, SubTab... subTabs) {
        this();
        addSubTabs(subTabs);
        setCompleter(completer);
    }

    /**
     * Adds a {@link SubTab} to this {@link ComplexTabCompleter}.
     * <p>
     * If there was a {@link SubTab} with the same name, the old {@link SubTab} is replaced by the new one.
     * </p>
     *
     * @param subTab the {@link SubTab} to add.
     */
    public void addSubTab(SubTab subTab) {
        subCompleters.put(subTab.getName(), subTab.getCompleter());
        for (String alias : subTab.getAliases()) {
            subCompleters.put(alias, subTab.getCompleter());
        }
    }

    /**
     * Adds {@link SubTab}s to this {@link ComplexTabCompleter}.
     * <p>
     * If there was a {@link SubTab} with the same name or if two or mode of the given {@link SubTab}s had the same name;<br/>
     * only the last one is stored.
     * </p>
     *
     * @param subTabs the {@link SubTab}s to add.
     */
    public void addSubTabs(SubTab... subTabs) {
        for (SubTab subTab : subTabs) {
            addSubTab(subTab);
        }
    }

    /**
     * Sets the {@link TabCompleter} for this {@link ComplexTabCompleter}.
     *
     * @param completer the {@link TabCompleter} to set for this {@link ComplexTabCompleter}.
     */
    public void setCompleter(TabCompleter completer) {
        this.completer = completer == null ? DEFAULT_COMPLETER : completer;
    }

    /**
     * Gives the list of possible completions for a typed command.
     * <p>
     * <ol>
     * <li>
     * If the first argument if the name of a {@link SubTab} :<br/>
     * Gives the list of possible completions for that {@link SubTab}.
     * </li>
     * <li>
     * If there's only one argument that is NOT the name of a {@link SubTab} :<br/>
     * Adds all the {@link SubTab}s names beginning with that argument to the completion list.
     * </li>
     * <li>
     * If no {@link SubTab} names were starting with the given argument :<br/>
     * Gives the list of completion returned by the {@link TabCompleter} of this {@link ComplexTabCompleter}.
     * </li>
     * </ol>
     * </p>
     * <p>
     * This means that it will first look for {@link SubTab} name completion.<br/>
     * And only if no {@link SubTab} could be completed, looks for this' {@link TabCompleter} completion.
     * </p>
     *
     * @param commandSender the {@link CommandSender}.
     * @param command       the sent {@link Command}.
     * @param s             the alias of this {@link Command}.
     * @param strings       the arguments of the {@link Command}.
     * @return a list of possible completions or null for the default Bukkit completion.
     */
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length > 0) {
            List<String> completion = new LinkedList<>();
            if (subCompleters.containsKey(strings[0])) {
                return subCompleters.get(strings[0]).onTabComplete(commandSender, command, s, Arrays.copyOfRange(strings, 1, strings.length));
            } else {
                if (strings.length == 1) {
                    for (String subCommand : subCompleters.keySet()) {
                        if (subCommand.startsWith(strings[0])) {
                            completion.add(subCommand);
                        }
                    }
                }
                if (completion.isEmpty()) {
                    return completer.onTabComplete(commandSender, command, s, strings);
                } else {
                    Collections.sort(completion);
                    return completion;
                }
            }
        } else {
            return null;
        }
    }
}
