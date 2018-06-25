package be.renaud11232.plugins.awesomecommands;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AwesomePlugin extends JavaPlugin {

    private Map<String, AwesomeCommand> awesomeCommands;

    public AwesomePlugin() {
        awesomeCommands = new HashMap<>();
    }

    @Override
    public void onEnable() {
        getDescription().getCommands().forEach((key, value) -> {
            var awesomeCommand = parseCommand(key, value);
            awesomeCommands.put(key, awesomeCommand);
            var command = getCommand(key);
            command.setExecutor((commandSender, command1, s, strings) -> awesomeCommand.execute(commandSender, s, strings));
            command.setTabCompleter((commandSender, command1, s, strings) -> awesomeCommand.tabComplete(commandSender, s, strings));
        });
    }

    public AwesomeCommand getAwesomeCommand(String name){
        var nameSplit = name.split(".", 2);
        return nameSplit.length == 1 ? awesomeCommands.get(nameSplit[0]) : awesomeCommands.get(nameSplit[0]).getSubCommand(nameSplit[1]);
    }

    private AwesomeCommand parseCommand(String name, Map<String, Object> properties) {
        var awesomeCommand = new AwesomeCommand(name);
        if(properties.containsKey("description")) {
            awesomeCommand.setDescription((String) properties.get("description"));
        }
        if(properties.containsKey("aliases")) {
            var aliases = properties.get("aliases");
            awesomeCommand.setAliases(aliases instanceof String ? Collections.singletonList((String) aliases) : (List) aliases);
        }
        if(properties.containsKey("permission")) {
            awesomeCommand.setPermission((String) properties.get("permission"));
        }
        if(properties.containsKey("permission-message")) {
            awesomeCommand.setPermissionMessage((String) properties.get("permission-message"));
        }
        if(properties.containsKey("usage")) {
            awesomeCommand.setUsage((String) properties.get("usage"));
        }
        if(properties.containsKey("subcommands")) {
            ((Map<String, Map<String, Object>>) properties.get("subcommands")).forEach((key, value) -> awesomeCommand.addSubCommand(parseCommand(key, value)));
        }
        return awesomeCommand;
    }
}
