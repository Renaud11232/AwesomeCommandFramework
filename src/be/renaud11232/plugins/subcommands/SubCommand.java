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

/**
 * Defines a {@link ComplexCommandExecutor}'s {@link SubCommand}.
 */
public class SubCommand extends SubElement<CommandExecutor> {

    public SubCommand(String name, String... aliases) {
        super(name, aliases);
    }

    public SubCommand(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    public SubCommand(String name, Permission permission, String... aliases) {
        super(name, permission, aliases);
    }

    public SubCommand(String name, CommandExecutor element, String... aliases) {
        super(name, element, aliases);
    }

    public SubCommand(String name, Permission permission, CommandExecutor element, String... aliases) {
        super(name, permission, element, aliases);
    }

    public SubCommand(String name, String permission, CommandExecutor element, String... aliases) {
        super(name, permission, element, aliases);
    }

    @Override
    protected CommandExecutor transformElement(CommandExecutor element) {
        return (sender, command, alias, args) -> {
            if(getPermission() == null || sender.hasPermission(getPermission())){
                if(element == null){
                    return ComplexCommandExecutor.DEFAULT_EXECUTOR.onCommand(sender, command, alias, args);
                }else{
                    return element.onCommand(sender, command, alias, args);
                }
            }else{
                sender.sendMessage(ChatColor.RED + "Sorry, you don't have permission to use that command");
                return true;
            }
        };
    }
}
