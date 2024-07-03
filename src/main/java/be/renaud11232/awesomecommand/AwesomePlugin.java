package be.renaud11232.awesomecommand;

import be.renaud11232.awesomecommand.annotation.command.AwesomeCommand;
import be.renaud11232.awesomecommand.annotation.command.AwesomeCommands;
import be.renaud11232.awesomecommand.util.AnnotationUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The {@link AwesomePlugin} class is the base class for any plugin that wants to make use of the AwesomeCommandFramework
 * This class is meant to be used the same way as the usual {@link JavaPlugin} class, and just automatically registers
 * subcommands and executes them
 */
@SuppressWarnings("unused")
public abstract class AwesomePlugin extends JavaPlugin {

    private final Map<String, Class<?>> commands = new HashMap<>();

    public AwesomePlugin() {
        super();
        initCommands();
    }

    protected AwesomePlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
        initCommands();
    }

    private void initCommands() {
        AwesomeCommands awesomeCommands = getClass().getAnnotation(AwesomeCommands.class);
        Class<?>[] commandClasses = awesomeCommands == null ? new Class<?>[0] : awesomeCommands.value();
        Arrays.stream(commandClasses).forEach(commandClass -> {
            AwesomeCommand awesomeCommand = AnnotationUtil.getCommandAnnotation(commandClass);
            commands.put(awesomeCommand.name(), commandClass);
        });
    }

    @Override
    public final boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (commands.containsKey(command.getName())) {
            return new ComplexCommand(commands.get(command.getName())).execute(sender, label, args);
        } else {
            return super.onCommand(sender, command, label, args);
        }
    }

    @Override
    public final List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (commands.containsKey(command.getName())) {
            return new ComplexCommand(commands.get(command.getName())).tabComplete(sender, alias, args);
        } else {
            return super.onTabComplete(sender, command, alias, args);
        }
    }

}
