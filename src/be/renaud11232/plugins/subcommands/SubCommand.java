package be.renaud11232.plugins.subcommands;

import org.bukkit.command.CommandExecutor;

import java.util.Arrays;

public class SubCommand {

    private String name;
    private CommandExecutor executor;
    private String[] aliases;

    public SubCommand(String name, CommandExecutor executor, String... aliases) {
        setName(name);
        setExecutor(executor);
        setAliases(aliases);
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

    public void setName(String name) {
        if (name == null) {
            throw new NullPointerException("Could not set the name, name was null");
        }
        if (name.contains(" ")) {
            throw new IllegalArgumentException("Could not set the name, name contains a \" \" : \"" + name + "\"");
        }
        this.name = name;
    }

    public void setExecutor(CommandExecutor executor) {
        this.executor = executor == null ? ComplexCommandExecutor.DEFAULT_EXECUTOR : executor;
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
