package be.renaud11232.awesomecommand.util;

import be.renaud11232.awesomecommand.annotation.command.AwesomeCommand;

import java.util.Objects;

/**
 * Utility class to work with annotation instances
 */
public class AnnotationUtil {

    private AnnotationUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the {@link AwesomeCommand} annotation of a given command specification
     *
     * @param commandClass the command specification class
     * @return the {@link AwesomeCommand} annotation instance
     * @throws NullPointerException if the command specification class is not annotated with @{@link AwesomeCommand}
     */
    public static AwesomeCommand getCommandAnnotation(Class<?> commandClass) {
        return Objects.requireNonNull(commandClass.getAnnotation(AwesomeCommand.class));
    }

}
