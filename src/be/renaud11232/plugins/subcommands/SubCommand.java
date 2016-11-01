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
import org.bukkit.command.CommandExecutor;
import org.bukkit.permissions.Permission;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Defines a {@link ComplexCommandExecutor}'s {@link SubCommand}.
 */
public class SubCommand {

    private String name;
    private String permission;
    private CommandExecutor executor;
    private List<String> aliases;

    public SubCommand(String name, String... aliases){
        this(name, (String) null, null, aliases);
    }

    public SubCommand(String name, String permission, String... aliases){
        this(name, permission, null, aliases);
    }

    public SubCommand(String name, Permission permission, String... aliases){
        this(name, permission, null, aliases);
    }

    public SubCommand(String name, CommandExecutor executor, String... aliases){
        this(name, (String) null, executor, aliases);
    }

    public SubCommand(String name, Permission permission, CommandExecutor executor, String... aliases) {
        this(name, (String) null, executor, aliases);
        setPermission(permission);
    }

    public SubCommand(String name, String permission, CommandExecutor executor, String... aliases) {
        this.aliases = new ArrayList<>();
        setName(name);
        setPermission(permission);
        setExecutor(executor);
        addAliases(aliases);
    }

    /**
     * Gets the name of this {@link SubCommand}.
     *
     * @return the name of this {@link SubCommand}.
     */
    public String getName() {
        return name;
    }

    public void setName(String name){
        Objects.requireNonNull(name, "Could not set the SubCommand name, it was null");
        if (name.contains(" ")) {
            throw new IllegalArgumentException("Could not set the SubCommand name, name contains a \" \" : \"" + name + "\"");
        }
        this.name = name;
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

    /**
     * Gets the {@link CommandExecutor} of this {@link SubCommand}.
     *
     * @return the {@link CommandExecutor} of this {@link SubCommand}.
     */
    public CommandExecutor getExecutor() {
        return executor;
    }

    public void setExecutor(CommandExecutor executor){
        this.executor = (sender, command, alias, args) -> {
            if(permission == null || sender.hasPermission(permission)){
                if(executor == null){
                    return ComplexCommandExecutor.DEFAULT_EXECUTOR.onCommand(sender, command, alias, args);
                }else{
                    return executor.onCommand(sender, command, alias, args);
                }
            }else{
                sender.sendMessage(ChatColor.RED + "Sorry, you don't have permission to use that command");
                return true;
            }
        };
    }

    /**
     * Gets all the aliases of this {@link SubCommand}.
     *
     * @return a copy the aliases of this {@link SubCommand}.
     */
    public List<String> getAliases() {
        return new ArrayList<>(aliases);
    }

    public void addAlias(String alias){
        Objects.requireNonNull(alias, "Could not add a SubCommand alias, alias was null");
        if (alias.contains(" ")) {
            throw new IllegalArgumentException("Could not add a SubCommand alias, alias contains a \" \" : \"" + alias + "\"");
        }
        aliases.add(alias);
    }

    public void addAliases(String... aliases){
        Objects.requireNonNull(aliases, "Could not add SubCommand aliases, aliases was null");
        for (String alias : aliases) {
            addAlias(alias);
        }
    }

    public boolean removeAlias(String alias){
        return aliases.remove(alias);
    }

}
