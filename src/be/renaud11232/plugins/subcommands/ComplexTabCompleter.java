/*
 * Copyright (c) 2016 R. Gaspard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package be.renaud11232.plugins.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.permissions.Permission;

import java.util.*;

/**
 * Defines a {@link TabCompleter} which can complete a command with some {@link SubTab}s.
 * <p>
 * A {@link ComplexTabCompleter} works as follows :<br>
 * </p>
 * <ol>
 * <li>
 * Checks for fully matching {@link SubTab}.<br>
 * If there is any, calls the {@link TabCompleter} of that {@link SubTab}.
 * </li>
 * <li>
 * If not,<br>
 * Looks for completion to match the beginning of a {@link SubTab} name.
 * </li>
 * <li>
 * If no {@link SubTab} matched the beginning of the first argument,<br>
 * Calls the {@link TabCompleter} of this {@link ComplexTabCompleter}.
 * </li>
 * </ol>
 */
public class ComplexTabCompleter extends ComplexElement<TabCompleter> implements TabCompleter {

    /**
     * {@link TabCompleter} that works the same as the default {@link TabCompleter}.
     * <p>
     * It does nothing and always returns <code>null</code>.
     * </p>
     */
    public static final TabCompleter DEFAULT_COMPLETER = (commandSender, command, s, strings) -> null;

    /**
     * {@link TabCompleter} that always returns an empty list.
     */
    public static final TabCompleter NO_TAB_COMPLETER = (commandSender, command, s, strings) -> Collections.emptyList();

    public ComplexTabCompleter(SubElement<TabCompleter>... subElements) {
        super(subElements);
    }

    public ComplexTabCompleter(String permission, SubElement<TabCompleter>... subElements) {
        super(permission, subElements);
    }

    public ComplexTabCompleter(Permission permission, SubElement<TabCompleter>... subElements) {
        super(permission, subElements);
    }

    public ComplexTabCompleter(TabCompleter executor, SubElement<TabCompleter>... subElements) {
        super(executor, subElements);
    }

    public ComplexTabCompleter(String permission, TabCompleter executor, SubElement<TabCompleter>... subElements) {
        super(permission, executor, subElements);
    }

    public ComplexTabCompleter(Permission permission, TabCompleter executor, SubElement<TabCompleter>... subElements) {
        super(permission, executor, subElements);
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(getPermission() == null || commandSender.hasPermission(getPermission())) {
            List<String> completion = new LinkedList<>();
            if (getSubElements().containsKey(strings[0])) {
                return getSubElements().get(strings[0]).onTabComplete(commandSender, command, s, Arrays.copyOfRange(strings, 1, strings.length));
            } else {
                if (strings.length == 1) {
                    getSubElements().keySet().stream().filter(subCommand -> subCommand.startsWith(strings[0])).forEach(completion::add);
                }
                if (completion.isEmpty()) {
                    return get().onTabComplete(commandSender, command, s, strings);
                } else {
                    Collections.sort(completion);
                    return completion;
                }
            }
        } else {
            return NO_TAB_COMPLETER.onTabComplete(commandSender, command, s, strings);
        }
    }

    @Override
    public TabCompleter transformNull(TabCompleter element) {
        return element == null ? DEFAULT_COMPLETER : element;
    }
}
