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

import org.bukkit.command.TabCompleter;
import org.bukkit.permissions.Permission;

/**
 * Defines a {@link ComplexTabCompleter}'s {@link SubTab}.
 */
public class SubTab extends SubElement<TabCompleter> {

    public SubTab(String name, String... aliases) {
        super(name, aliases);
    }

    public SubTab(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    public SubTab(String name, Permission permission, String... aliases) {
        super(name, permission, aliases);
    }

    public SubTab(String name, TabCompleter element, String... aliases) {
        super(name, element, aliases);
    }

    public SubTab(String name, Permission permission, TabCompleter element, String... aliases) {
        super(name, permission, element, aliases);
    }

    public SubTab(String name, String permission, TabCompleter element, String... aliases) {
        super(name, permission, element, aliases);
    }

    @Override
    public TabCompleter transformNull(TabCompleter element) {
        return (commandSender, command, alias, args) -> {
            if(getPermission() == null || commandSender.hasPermission(getPermission())){
                if(element == null){
                    return ComplexTabCompleter.DEFAULT_COMPLETER.onTabComplete(commandSender, command, alias, args);
                }else{
                    return element.onTabComplete(commandSender, command, alias, args);
                }
            }else{
                return ComplexTabCompleter.NO_TAB_COMPLETER.onTabComplete(commandSender, command, alias, args);
            }
        };
    }
}
