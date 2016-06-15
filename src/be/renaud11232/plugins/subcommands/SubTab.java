package be.renaud11232.plugins.subcommands;

import org.bukkit.command.TabCompleter;

import java.util.Arrays;

public class SubTab {

    private String name;
    private TabCompleter completer;
    private String[] aliases;

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
            if (alias.contains(" ")) {
                throw new IllegalArgumentException("Could not create a SubTab, at least one of the given aliases contains a \" \" : \"" + alias + "\"");
            }
        }
        this.name = name;
        this.completer = completer == null ? ComplexTabCompleter.DEFAULT_COMPLETER : completer;
        this.aliases = Arrays.copyOf(aliases, aliases.length);
    }

    public String getName() {
        return name;
    }

    public TabCompleter getCompleter() {
        return completer;
    }

    public String[] getAliases() {
        return Arrays.copyOf(aliases, aliases.length);
    }
}
