package be.renaud11232.awesomecommand;

/**
 * This interface is meant to signal that a class annotated with {@link be.renaud11232.awesomecommand.annotation.command.AwesomeCommand} is meant to be used directly as a command executor.
 * This is similar to how @{@link org.bukkit.command.CommandExecutor} works, but instead of method parameters, the command arguments are put into class fields.
 */
public interface AwesomeCommandExecutor {

    /**
     * Executes the command typed by a user
     *
     * @return true if the command was valid, false otherwise
     */
    boolean execute();

}
