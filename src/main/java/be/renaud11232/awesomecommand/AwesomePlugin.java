package be.renaud11232.awesomecommand;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;


public abstract class AwesomePlugin extends JavaPlugin {

    public void initCommand(Class<?> commandClass) {
        be.renaud11232.awesomecommand.annotation.AwesomeCommand annotation = Util.getCommandAnnotation(commandClass);
        PluginCommand pluginCommand = getCommand(annotation.name());
        ComplexCommand awesomeCommand = new ComplexCommand(commandClass);
        pluginCommand.setExecutor((commandSender, command, s, strings) -> awesomeCommand.execute(commandSender, s, strings));
        pluginCommand.setTabCompleter((commandSender, command, s, strings) -> awesomeCommand.tabComplete(commandSender, s, strings));
    }

}
