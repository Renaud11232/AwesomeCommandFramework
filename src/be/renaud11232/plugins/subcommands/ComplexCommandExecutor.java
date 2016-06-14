package be.renaud11232.plugins.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.*;

public class ComplexCommandExecutor implements CommandExecutor{

    private Map<String, CommandExecutor> subCommands;
    private CommandExecutor executor;

    public ComplexCommandExecutor(){
        subCommands = new HashMap<>();
        executor = null;
    }

    public ComplexCommandExecutor(CommandExecutor executor, SubCommand... subCommands){
        this();
        setExecutor(executor);
        addSubCommands(subCommands);
    }

    public void addSubCommand(SubCommand subCommand){
        subCommands.put(subCommand.getName(), subCommand.getExecutor());
        for(String alias : subCommand.getAliases()){
            subCommands.put(alias, subCommand.getExecutor());
        }
    }

    public void addSubCommands(SubCommand... subCommands){
        for(SubCommand sub : subCommands){
            addSubCommand(sub);
        }
    }

    public void setExecutor(CommandExecutor executor){
        this.executor = executor;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 0 || !subCommands.containsKey(strings[0])){
            return executor != null && executor.onCommand(commandSender, command, s, strings);
        }else{
            return subCommands.get(strings[0]).onCommand(commandSender, command, s, strings.length == 1 ? new String[0] : Arrays.copyOfRange(strings, 1, strings.length -1));
        }
    }
    //TODO rework this
    /*
    public TabCompleter getTabCompleter(){
        return new TabCompleter() {
            @Override
            public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
                List<String> completion = new LinkedList<>();
                if(strings.length == 0){
                    completion.addAll(subCommands.keySet());
                    return completion;
                }
                if(subCommands.containsKey(strings[0]) && subCommands.get(strings[0]) instanceof ComplexCommandExecutor){
                    return ((ComplexCommandExecutor)subCommands.get(strings[0])).getTabCompleter().onTabComplete(commandSender, command, s, strings.length == 1 ? new String[0] : Arrays.copyOfRange(strings, 1, strings.length -1));
                }
                if(!subCommands.containsKey(strings[0])) {
                    completion = new LinkedList<>();
                    for (String str : subCommands.keySet()) {
                        if (str.startsWith(strings[0])) {
                            completion.add(str);
                        }
                    }
                }
                return completion;
            }
        };
    }*/
}
