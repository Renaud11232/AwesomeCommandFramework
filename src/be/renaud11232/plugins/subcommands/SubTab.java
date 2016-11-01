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

import org.bukkit.command.TabCompleter;
import org.bukkit.permissions.Permission;

import java.util.*;

/**
 * Defines a {@link ComplexTabCompleter}'s {@link SubTab}.
 */
public class SubTab {

    private String name;
    private String permission;
    private TabCompleter completer;
    private List<String> aliases;

    public SubTab(String name, String...aliases){
        this(name, (String) null, null, aliases);
    }

    public SubTab(String name, String permission, String... aliases){
        this(name, permission, null, aliases);
    }

    public SubTab(String name, Permission permission, String... aliases){
        this(name, permission, null, aliases);
    }

    public SubTab(String name, TabCompleter completer, String... aliases){
        this(name, (String) null, completer, aliases);
    }

    public SubTab(String name, Permission permission, TabCompleter completer, String... aliases){
        this(name, (String) null, completer, aliases);
        setPermission(permission);
    }

    public SubTab(String name, String permission, TabCompleter completer, String... aliases){
        this.aliases = new ArrayList<>();
        setName(name);
        setPermission(permission);
        setCompleter(completer);
        addAliases(aliases);
    }

    /**
     * Gets the name of this {@link SubTab}.
     *
     * @return the name of this {@link SubTab}.
     */
    public String getName() {
        return name;
    }

    public void setName(String name){
        Objects.requireNonNull(name, "Could not set the SubTab name, it was null");
        if(name.contains(" ")){
            throw new IllegalArgumentException("Could not set the SubTab name, name contains a \" \" : \"" + name + "\"");
        }
        this.name = name;
    }

    public void setPermission(String permission){
        this.permission = permission;
    }

    public void setPermission(Permission permission){
        this.permission = permission == null ? null : permission.getName();
    }

    /**
     * Gets the {@link TabCompleter} of this {@link SubTab}.
     *
     * @return the {@link TabCompleter} of this {@link SubTab}.
     */
    public TabCompleter getCompleter() {
        return completer;
    }

    public void setCompleter(TabCompleter completer){
        this.completer = (commandSender, command, alias, args) -> {
            if(permission == null || commandSender.hasPermission(permission)){
                if(completer == null){
                    return ComplexTabCompleter.DEFAULT_COMPLETER.onTabComplete(commandSender, command, alias, args);
                }else{
                    return completer.onTabComplete(commandSender, command, alias, args);
                }
            }else{
                return ComplexTabCompleter.NO_TAB_COMPLETER.onTabComplete(commandSender, command, alias, args);
            }
        };
    }

    /**
     * Gets all the aliases of this {@link SubTab}.
     *
     * @return a copy the aliases of this {@link SubTab}.
     */
    public List<String> getAliases() {
        return new ArrayList<>(aliases);
    }

    public void addAlias(String alias){
        Objects.requireNonNull(alias, "Could not add a SubTab alias, alias was null");
        if(alias.contains(" ")) {
            throw new IllegalArgumentException("Could not add a SubTab alias, alias contains a \" \" : \"" + alias + "\"");
        }
        aliases.add(alias);
    }

    public void addAliases(String... aliases){
        Objects.requireNonNull(aliases, "Could not add SubTab aliases, aliases was null");
        for(String alias : aliases){
            addAliases(alias);
        }
    }

    public boolean removeAlias(String alias){
        return aliases.remove(alias);
    }
}
