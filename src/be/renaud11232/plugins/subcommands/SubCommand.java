package be.renaud11232.plugins.subcommands;

import org.bukkit.command.CommandExecutor;

import java.util.Arrays;

public class SubCommand {

    private String name;
    private CommandExecutor executor;
    private String[] aliases;

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
            if (alias.contains(" ")) {
                throw new IllegalArgumentException("Could not create a SubCommand, at least one of the given aliases contains a \" \" : \"" + alias + "\"");
            }
        }
        this.name = name;
        this.executor = executor == null ? ComplexCommandExecutor.DEFAULT_EXECUTOR : executor;
        this.aliases = aliases;
    }

    public String getName() {
        return name;
    }

    public CommandExecutor getExecutor() {
        return executor;
    }

    public String[] getAliases() {
        return Arrays.copyOf(aliases, aliases.length);
    }

}
