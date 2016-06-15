package be.renaud11232.plugins.subcommands;

import org.bukkit.command.TabCompleter;

import java.util.Arrays;

/**
 * Defines a {@link ComplexTabCompleter}'s {@link SubTab}.
 */
public class SubTab {

    private String name;
    private TabCompleter completer;
    private String[] aliases;

    /**
     * Construncts a new {@link SubTab} with a given name, {@link TabCompleter}, and some aliases.
     *
     * @param name     the name of the {@link SubTab}.
     * @param completer the {@link TabCompleter} of the {@link SubTab}, ComplexTabCompleter.DEFAULT_COMPLETER is used if the value is <code>null</code>.
     * @param aliases  the aliases of the {@link SubTab}.
     * @throws NullPointerException     if the name, the aliases array or one of the aliases is null.
     * @throws IllegalArgumentException if the name or one of the aliases contains a space.
     */
    public SubTab(String name, TabCompleter completer, String... aliases) {
        if (name == null) {
            throw new NullPointerException("Could not create a SubTab, name was null");
        }
        if (name.contains(" ")) {
            throw new IllegalArgumentException("Could not create a SubTab, name contains a \" \" : \"" + name + "\"");
        }
        if (aliases == null) {
            throw new NullPointerException("Could not create a SubTab, aliases was null");
        }
        for (String alias : aliases) {
            if (alias == null) {
                throw new NullPointerException("Could not create a SubTab, at least on of the given aliases was null");
            }
            if (alias.contains(" ")) {
                throw new IllegalArgumentException("Could not create a SubTab, at least one of the given aliases contains a \" \" : \"" + alias + "\"");
            }
        }
        this.name = name;
        this.completer = completer == null ? ComplexTabCompleter.DEFAULT_COMPLETER : completer;
        this.aliases = Arrays.copyOf(aliases, aliases.length);
    }


    /**
     * Gets the name of this {@link SubTab}.
     *
     * @return the name of this {@link SubTab}.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the {@link TabCompleter} of this {@link SubTab}.
     *
     * @return the {@link TabCompleter} of this {@link SubTab}.
     */
    public TabCompleter getCompleter() {
        return completer;
    }

    /**
     * Gets all the aliases of this {@link SubTab}.
     *
     * @return a copy the aliases of this {@link SubTab}.
     */
    public String[] getAliases() {
        return Arrays.copyOf(aliases, aliases.length);
    }
}
