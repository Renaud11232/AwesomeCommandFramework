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
