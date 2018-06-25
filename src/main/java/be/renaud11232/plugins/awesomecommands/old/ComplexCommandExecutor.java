/*
 * Copyright (c) 2016 R. Gaspard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package be.renaud11232.plugins.awesomecommands.old;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import java.util.Arrays;

/**
 * Defines a {@link CommandExecutor} which can execute a command and some {@link SubCommandExecutor}s.
 * <p>
 * A {@link ComplexCommandExecutor} works as follows :
 * </p>
 * <ol>
 * <li>
 * If there's no argument or the first argument does not match a {@link SubCommandExecutor} name :<br>
 * The {@link CommandExecutor} of this {@link ComplexCommandExecutor} is executed.
 * </li>
 * <li>
 * Otherwise (If there is an argument and it matches a {@link SubCommandExecutor} name) :<br>
 * The {@link CommandExecutor} of the matching {@link SubCommandExecutor} is executed.
 * </li>
 * </ol>
 */
public class ComplexCommandExecutor extends ComplexElement<CommandExecutor> implements CommandExecutor {

    /**
     * {@link CommandExecutor} that works the same as the default {@link CommandExecutor}.
     * <p>
     * It does nothing and always returns <code>false</code>.
     * </p>
     */
    public static final CommandExecutor DEFAULT_EXECUTOR = (commandSender, command, s, strings) -> false;

    /**
     * {@link CommandExecutor} that does nothing and always returns <code>true</code>.
     */
    public static final CommandExecutor NO_EXECUTOR = (commandSender, command, s, strings) -> true;

    public ComplexCommandExecutor(SubElement<CommandExecutor>... subElements) {
        super(subElements);
    }

    public ComplexCommandExecutor(String permission, SubElement<CommandExecutor>... subElements) {
        super(permission, subElements);
    }

    public ComplexCommandExecutor(Permission permission, SubElement<CommandExecutor>... subElements) {
        super(permission, subElements);
    }

    public ComplexCommandExecutor(CommandExecutor executor, SubElement<CommandExecutor>... subElements) {
        super(executor, subElements);
    }

    public ComplexCommandExecutor(String permission, CommandExecutor executor, SubElement<CommandExecutor>... subElements) {
        super(permission, executor, subElements);
    }

    public ComplexCommandExecutor(Permission permission, CommandExecutor executor, SubElement<CommandExecutor>... subElements) {
        super(permission, executor, subElements);
    }

    /**
     * Executes this {@link ComplexCommandExecutor}.
     * <ol>
     * <li>
     * If there's no argument or the first argument does not match a {@link SubCommandExecutor} name :<br>
     * The {@link CommandExecutor} of this {@link ComplexCommandExecutor} is executed.
     * </li>
     * <li>
     * Otherwise (If there is an argument and it matches a {@link SubCommandExecutor} name) :<br>
     * The {@link CommandExecutor} of the matching {@link SubCommandExecutor} is executed.
     * </li>
     * </ol>
     * <p>
     * This means if you have a {@link SubCommandExecutor} with a given name, the {@link CommandExecutor} of this {@link ComplexCommandExecutor} will never get that given name as an argument.<br>
     * It will always call the {@link SubCommandExecutor} executor instead.
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
        if(getPermission() == null || commandSender.hasPermission(getPermission())) {
            if (strings.length == 0 || !getSubElements().containsKey(strings[0])) {
                return get().onCommand(commandSender, command, s, strings);
            } else {
                return getSubElements().get(strings[0]).onCommand(commandSender, command, s, Arrays.copyOfRange(strings, 1, strings.length));
            }
        } else{
            commandSender.sendMessage(ChatColor.RED + "Sorry, you don't have permission to use that command");
            return true;
        }
    }

    @Override
    public CommandExecutor transformNull(CommandExecutor element) {
        return element == null ? DEFAULT_EXECUTOR : element;
    }
}
