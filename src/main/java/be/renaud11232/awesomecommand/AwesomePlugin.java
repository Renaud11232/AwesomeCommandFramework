package be.renaud11232.awesomecommand;

import be.renaud11232.awesomecommand.annotation.CommandPackage;
import be.renaud11232.awesomecommand.annotation.command.AwesomeCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


/**
 * The {@link AwesomePlugin} class is the base class for any plugin that wants to make use of the AwesomeCommandFramework
 * This class is meant to be used the same way as the usual {@link JavaPlugin} class, and just provides one additional utility to initialize commands
 */
@SuppressWarnings("unused")
public abstract class AwesomePlugin extends JavaPlugin {

    private final Map<String, Class<?>> commands = new HashMap<>();

    public AwesomePlugin() {
        super();
        init();
    }

    protected AwesomePlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
        init();
    }

    private void init() {
        CommandPackage commandPackage = getClass().getAnnotation(CommandPackage.class);
        String packageName;
        if (commandPackage == null || commandPackage.value().isEmpty()) {
            packageName = getClass().getPackage().getName();
        } else {
            packageName = commandPackage.value();
        }
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream(packageName.replaceAll("[.]", "/"))) {
            if (stream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                Set<Class<?>> foundCommands = reader.lines()
                        .filter(line -> line.endsWith(".class"))
                        .map(className -> getClass(className, packageName))
                        .filter(Objects::nonNull)
                        .filter(c -> Objects.nonNull(c.getAnnotation(AwesomeCommand.class)))
                        .collect(Collectors.toSet());
                foundCommands.stream()
                        .filter(command -> foundCommands.stream().noneMatch(c -> Arrays.asList(c.getAnnotation(AwesomeCommand.class).subCommands()).contains(command)))
                        .forEach(command -> commands.put(command.getAnnotation(AwesomeCommand.class).name(), command));
            }
        } catch (IOException ignored) {
        }
    }

    private Class<?> getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "." + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            return null;
        }
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
