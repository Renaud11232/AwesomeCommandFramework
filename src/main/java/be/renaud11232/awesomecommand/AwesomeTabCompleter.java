package be.renaud11232.awesomecommand;

import java.util.List;

/**
 * This interface is meant to signal that a class annotated with {@link be.renaud11232.awesomecommand.annotation.command.AwesomeCommand} is meant to be used directly as a tab completer.
 * This is similar to how @{@link org.bukkit.command.TabCompleter} works, but instead of method parameters, the command arguments are put into class fields.
 */
public interface AwesomeTabCompleter {

    /**
     * Provides completion options that will be shown to the user
     *
     * @return a {@link List} of {@link String} representing the possible completion options for the current command
     */
    List<String> tabComplete();

}
