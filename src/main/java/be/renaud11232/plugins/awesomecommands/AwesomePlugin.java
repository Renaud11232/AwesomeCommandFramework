package be.renaud11232.plugins.awesomecommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class AwesomePlugin extends JavaPlugin {

    private AwesomeCommandMap commandMap;

    public AwesomePlugin() {
        commandMap = new AwesomeCommandMap();
        getDescription().getCommands().forEach((key, value) -> {
            commandMap.putCommand(parseCommand(key, value));
        });
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

    @Override
    public final boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return getAwesomeCommand(command.getName()).execute(sender, label, args);
    }

    @Override
    public final List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return getAwesomeCommand(command.getName()).tabComplete(sender, alias, args);
    }

    @Override
    @Deprecated
    public PluginCommand getCommand(String name) {
        throw new UnsupportedOperationException("This method has been replaced by the much more awesome 'getAwesomeCommand(String)' method");
    }

    public AwesomeCommand getAwesomeCommand(String name){
        return commandMap.getCommand(name);
    }
}
