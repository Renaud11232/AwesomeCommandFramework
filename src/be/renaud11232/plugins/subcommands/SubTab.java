package be.renaud11232.plugins.subcommands;

import org.bukkit.command.TabCompleter;

import java.util.Arrays;

public class SubTab {

    private String name;
    private TabCompleter completer;
    private String[] aliases;

    public SubTab(String name, TabCompleter completer, String... aliases) {
        setName(name);
        setCompleter(completer);
        setAliases(aliases);
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

    public void setName(String name) {
        if (name == null) {
            throw new NullPointerException("Could not set the name, name was null");
        }
        if (name.contains(" ")) {
            throw new IllegalArgumentException("Could not set the name, name contains a \" \" : \"" + name + "\"");
        }
        this.name = name;
    }

    public void setCompleter(TabCompleter completer) {
        this.completer = completer == null ? ComplexTabCompleter.DEFAULT_COMPLETER : completer;
    }

    public void setAliases(String... aliases) {
        if (aliases == null) {
            throw new NullPointerException("Could not set the aliases, aliases was null");
        }
        for (String alias : aliases) {
            if (alias.contains(" ")) {
                throw new IllegalArgumentException("Could not set the aliases, at least one of the given aliases contains a \" \" : \"" + alias + "\"");
            }
        }
        this.aliases = Arrays.copyOf(aliases, aliases.length);
    }
}
