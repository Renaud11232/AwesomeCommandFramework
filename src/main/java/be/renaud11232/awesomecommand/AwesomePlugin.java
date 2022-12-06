package be.renaud11232.awesomecommand;

import be.renaud11232.awesomecommand.annotation.command.AwesomeCommand;
import be.renaud11232.awesomecommand.annotation.util.AnnotationUtil;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * The {@link AwesomePlugin} class is the base class for any plugin that wants to make use of the AwesomeCommandFramework
 * This class is meant to be used the same way as the usual {@link JavaPlugin} class, and just provides one additional utility to initialize commands
 */
public abstract class AwesomePlugin extends JavaPlugin {

    /**
     * Initializes a plugin command based on its cammand specification passed as {@link Class} object.
     * The class must be annotated with the {@link AwesomeCommand} annotation and the name attribute must be the same as the one defined in plugin.yml
     * Every time the command is executed or a tab completion is requested, an object will be created and used to hold the command parameters.
     *
     * @param commandClass the command specification class
     */
    public void initCommand(Class<?> commandClass) {
        AwesomeCommand annotation = AnnotationUtil.getCommandAnnotation(commandClass);
        PluginCommand pluginCommand = getCommand(annotation.name());
        ComplexCommand awesomeCommand = new ComplexCommand(commandClass);
        pluginCommand.setExecutor((commandSender, command, s, strings) -> awesomeCommand.execute(commandSender, s, strings));
        pluginCommand.setTabCompleter((commandSender, command, s, strings) -> awesomeCommand.tabComplete(commandSender, s, strings));
    }

}
