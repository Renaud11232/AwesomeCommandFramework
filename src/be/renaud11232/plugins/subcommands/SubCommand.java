package be.renaud11232.plugins.subcommands;

import org.bukkit.command.CommandExecutor;

public class SubCommand {

    private String name;
    private CommandExecutor executor;
    private String[] aliases;

    public SubCommand(CommandExecutor executor, String name, String... aliases){
        if(executor == null){
            throw new NullPointerException("Could not create a SubCommand, executor was null");
        }
        if(name == null){
            throw new NullPointerException("Could not create a SubCommand, name was null");
        }
        if(name.contains(" ")){
            throw new IllegalArgumentException("Could not create a SubCommand, name contains a \" \" : \"" + name + "\"");
        }
        for(String alias : aliases){
            if(alias.contains(" ")){
                throw new IllegalArgumentException("Could not create a SubCommand, at least one of the given aliases contains a \" \" : \"" + alias + "\"");
            }
        }
        this.name = name;
        this.executor = executor;
        this.aliases = aliases;
    }

    public String getName(){
        return name;
    }

    public CommandExecutor getExecutor(){
        return executor;
    }

    public String[] getAliases(){
        return aliases;
    }

}
