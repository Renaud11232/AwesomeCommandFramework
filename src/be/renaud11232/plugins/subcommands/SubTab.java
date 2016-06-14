package be.renaud11232.plugins.subcommands;

import org.bukkit.command.TabCompleter;

import java.util.Arrays;

public class SubTab {

    private String name;
    private TabCompleter completer;
    private String[] aliases;

    public SubTab(String name, TabCompleter completer, String... aliases) {
        if (completer == null) {
            throw new NullPointerException("Could not create a SubTab, completer was null");
        }
        if (name == null) {
            throw new NullPointerException("Could not create a SubTab, name was null");
        }
        if (name.contains(" ")) {
            throw new IllegalArgumentException("Could not create a SubTab, name contains a \" \" : \"" + name + "\"");
        }
        for (String alias : aliases) {
            if (alias.contains(" ")) {
                throw new IllegalArgumentException("Could not create a SubTab, at least one of the given aliases contains a \" \" : \"" + alias + "\"");
            }
        }
        this.name = name;
        this.completer = completer;
        this.aliases = aliases;
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
