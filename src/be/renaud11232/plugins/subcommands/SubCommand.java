package be.renaud11232.plugins.subcommands;

import org.bukkit.command.CommandExecutor;

public class SubCommand {

    private String name;
    private CommandExecutor executor;

    public SubCommand(String name, CommandExecutor executor){
        this.name = name;
        this.executor = executor;
    }

    public String getName(){
        return name;
    }

    public CommandExecutor getExecutor(){
        return executor;
    }

}
