package be.renaud11232.plugins.awesomecommands;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Defines a command map for {@link AwesomeCommand}'s
 */
public class AwesomeCommandMap {

    private Map<String, AwesomeCommand> commands;

    /**
     * Constructs a new empty {@link AwesomeCommandMap}
     */
    public AwesomeCommandMap() {
        commands = new HashMap<>();
    }

    /**
     * Puts an {@link AwesomeCommand} in this {@link AwesomeCommandMap}
     * @param command the {@link AwesomeCommand} to add
     */
    public void putCommand(AwesomeCommand command) {
        commands.put(command.getName(), command);
        command.getAliases()
                .stream()
                .filter(alias -> !commands.containsKey(alias))
                .forEach(alias -> commands.put(alias, command));
    }

    /**
     * Removes an {@link AwesomeCommand} from this {@link AwesomeCommandMap}
     * @param awesomeCommand the {@link AwesomeCommand} to remove
     */
    public void removeCommand(AwesomeCommand awesomeCommand) {
        commands.remove(awesomeCommand.getName(), awesomeCommand);
        awesomeCommand.getAliases().forEach(alias -> commands.remove(alias, awesomeCommand));
    }

    /**
     * Removes all {@link AwesomeCommand}'s from this {@link AwesomeCommandMap}
     */
    public void clearCommands() {
        commands.clear();
    }

    /**
     * Gets an {@link AwesomeCommand} in this map based on its fully qualified name.
     * This can be a sub command using dotted notation
     * @param name the name of the command or sub command to get
     * @return the {@link AwesomeCommand} if it exists of null otherwise
     */
    public AwesomeCommand getCommand(String name) {
        var nameSplit = name.split("\\.", 2);
        return nameSplit.length == 1 ? commands.get(nameSplit[0]) : commands.get(nameSplit[0]).getSubCommand(nameSplit[1]);
    }

    /**
     * Checks whether a command or sub command exists in this {@link AwesomeCommandMap}
     * @param name the name of the command of sub command
     * @return true if the command exists, false otherwise
     */
    public boolean hasCommand(String name) {
        return commands.containsKey(name);
    }

    /**
     * Gets all the known command names and aliases
     * @return the known command names and aliases
     */
    public Collection<String> getKnownCommandNames() {
        return commands.keySet();
    }

    /**
     * Gets all the known {@link AwesomeCommand}'s
     * @return all the {@link AwesomeCommand}'s
     */
    public Collection<AwesomeCommand> getKnownCommands() {
        return commands.values();
    }

}
