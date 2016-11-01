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

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Defines a {@link CommandExecutor} which can execute a command and some {@link SubCommand}s.
 * <p>
 * A {@link ComplexCommandExecutor} works as follows :
 * </p>
 * <ol>
 * <li>
 * If there's no argument or the first argument does not match a {@link SubCommand} name :<br>
 * The {@link CommandExecutor} of this {@link ComplexCommandExecutor} is executed.
 * </li>
 * <li>
 * Otherwise (If there is an argument and it matches a {@link SubCommand} name) :<br>
 * The {@link CommandExecutor} of the matching {@link SubCommand} is executed.
 * </li>
 * </ol>
 */
public class ComplexCommandExecutor implements CommandExecutor {

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

    private String permission;
    private CommandExecutor executor;
    private Map<String, CommandExecutor> subCommands;

    public ComplexCommandExecutor(SubCommand... subCommands){
        this((String) null, null, subCommands);
    }

    public ComplexCommandExecutor(String permission, SubCommand... subCommands){
        this(permission, null, subCommands);
    }

    public ComplexCommandExecutor(Permission permission, SubCommand... subCommands){
        this(permission, null, subCommands);
    }

    public ComplexCommandExecutor(CommandExecutor executor, SubCommand... subCommands){
        this((String) null, executor, subCommands);
    }

    public ComplexCommandExecutor(String permission, CommandExecutor executor, SubCommand... subCommands){
        this.subCommands = new HashMap<>();
        setPermission(permission);
        setExecutor(executor);
        addSubCommands(subCommands);
    }

    public ComplexCommandExecutor(Permission permission, CommandExecutor executor, SubCommand... subCommands){
        this((String) null, executor, subCommands);
        setPermission(permission);
    }

    public String getPermission(){
        return permission;
    }

    public void setPermission(String permission){
        this.permission = permission;
    }

    public void setPermission(Permission permission){
        this.permission = permission == null ? null : permission.getName();
    }

    public CommandExecutor getExecutor(){
        return executor;
    }

    /**
     * Sets the {@link CommandExecutor} of this {@link ComplexCommandExecutor}.
     *
     * @param executor the {@link CommandExecutor} to set for this {@link ComplexCommandExecutor} if the given value is <code>null</code>, uses DEFAULT_EXECUTOR.
     */
    public void setExecutor(CommandExecutor executor) {
        this.executor = executor == null ? DEFAULT_EXECUTOR : executor;
    }

    public void removeSubCommand(SubCommand subCommand){
        subCommands.remove(subCommand.getName());
        subCommand.getAliases().forEach(subCommands::remove);
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
        subCommands.put(subCommand.getName(), subCommand.get());
        subCommand.getAliases().forEach(alias -> {
            subCommands.put(alias, subCommand.get());
        });
    }

    /**
     * Adds {@link SubCommand}s to this {@link ComplexCommandExecutor}.
     * <p>
     * If there was a {@link SubCommand} with the same name or if two or more of the given {@link SubCommand}s had the same name,<br>
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
     * Executes this {@link ComplexCommandExecutor}.
     * <ol>
     * <li>
     * If there's no argument or the first argument does not match a {@link SubCommand} name :<br>
     * The {@link CommandExecutor} of this {@link ComplexCommandExecutor} is executed.
     * </li>
     * <li>
     * Otherwise (If there is an argument and it matches a {@link SubCommand} name) :<br>
     * The {@link CommandExecutor} of the matching {@link SubCommand} is executed.
     * </li>
     * </ol>
     * <p>
     * This means if you have a {@link SubCommand} with a given name, the {@link CommandExecutor} of this {@link ComplexCommandExecutor} will never get that given name as an argument.<br>
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
        if(permission == null || commandSender.hasPermission(permission)) {
            if (strings.length == 0 || !subCommands.containsKey(strings[0])) {
                return executor.onCommand(commandSender, command, s, strings);
            } else {
                return subCommands.get(strings[0]).onCommand(commandSender, command, s, Arrays.copyOfRange(strings, 1, strings.length));
            }
        } else{
            commandSender.sendMessage(ChatColor.RED + "Sorry, you don't have permission to use that command");
            return true;
        }
    }
}
