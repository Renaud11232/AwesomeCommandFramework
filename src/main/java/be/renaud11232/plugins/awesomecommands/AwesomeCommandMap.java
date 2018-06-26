package be.renaud11232.plugins.awesomecommands;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AwesomeCommandMap {

    private Map<String, AwesomeCommand> commands;

    public AwesomeCommandMap() {
        commands = new HashMap<>();
    }

    public void putCommand(AwesomeCommand command) {
        commands.put(command.getName(), command);
        command.getAliases()
                .stream()
                .filter(alias -> !commands.containsKey(alias))
                .forEach(alias -> commands.put(alias, command));
    }

    public void removeCommand(AwesomeCommand awesomeCommand) {
        commands.remove(awesomeCommand.getName(), awesomeCommand);
        awesomeCommand.getAliases().forEach(alias -> commands.remove(alias, awesomeCommand));
    }

    public void clearCommands() {
        commands.clear();
    }

    public AwesomeCommand getCommand(String name) {
        var nameSplit = name.split("\\.", 2);
        return nameSplit.length == 1 ? commands.get(nameSplit[0]) : commands.get(nameSplit[0]).getSubCommand(nameSplit[1]);
    }

    public boolean hasCommand(String name) {
        return commands.containsKey(name);
    }

    public Collection<String> getKnownCommandNames() {
        return commands.keySet();
    }

    public Collection<AwesomeCommand> getKnownCommands() {
        return commands.values();
    }

}
