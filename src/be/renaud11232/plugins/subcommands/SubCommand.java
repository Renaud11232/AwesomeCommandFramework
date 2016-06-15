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

import org.bukkit.command.CommandExecutor;

import java.util.Arrays;

/**
 * Defines a {@link ComplexCommandExecutor}'s {@link SubCommand}.
 */
public class SubCommand {

    private String name;
    private CommandExecutor executor;
    private String[] aliases;

    /**
     * Construncts a new {@link SubCommand} with a given name, {@link CommandExecutor}, and some aliases.
     *
     * @param name     the name of the {@link SubCommand}.
     * @param executor the {@link CommandExecutor} of the {@link SubCommand}, ComplexCommandExecutor.DEFAULT_EXECUTOR is used if the value is <code>null</code>.
     * @param aliases  the aliases of the {@link SubCommand}.
     * @throws NullPointerException     if the name, the aliases array or one of the aliases is null.
     * @throws IllegalArgumentException if the name or one of the aliases contains a space.
     */
    public SubCommand(String name, CommandExecutor executor, String... aliases) {
        if (name == null) {
            throw new NullPointerException("Could not create a SubCommand, name was null");
        }
        if (name.contains(" ")) {
            throw new IllegalArgumentException("Could not create a SubCommand, name contains a \" \" : \"" + name + "\"");
        }
        if (aliases == null) {
            throw new NullPointerException("Could not create a SubCommand, aliases was null");
        }
        for (String alias : aliases) {
            if (alias == null) {
                throw new NullPointerException("Could not create a SubCommand, at least on of the given aliases was null");
            }
            if (alias.contains(" ")) {
                throw new IllegalArgumentException("Could not create a SubCommand, at least one of the given aliases contains a \" \" : \"" + alias + "\"");
            }
        }
        this.name = name;
        this.executor = executor == null ? ComplexCommandExecutor.DEFAULT_EXECUTOR : executor;
        this.aliases = Arrays.copyOf(aliases, aliases.length);
    }

    /**
     * Gets the name of this {@link SubCommand}.
     *
     * @return the name of this {@link SubCommand}.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the {@link CommandExecutor} of this {@link SubCommand}.
     *
     * @return the {@link CommandExecutor} of this {@link SubCommand}.
     */
    public CommandExecutor getExecutor() {
        return executor;
    }

    /**
     * Gets all the aliases of this {@link SubCommand}.
     *
     * @return a copy the aliases of this {@link SubCommand}.
     */
    public String[] getAliases() {
        return Arrays.copyOf(aliases, aliases.length);
    }

}
