package be.renaud11232.awesomecommand.annotation.util;

import be.renaud11232.awesomecommand.annotation.command.AwesomeCommand;

import java.util.Objects;

public class AnnotationUtil {

    private AnnotationUtil() {
        throw new UnsupportedOperationException();
    }

    public static AwesomeCommand getCommandAnnotation(Class<?> commandClass) {
        return Objects.requireNonNull(commandClass.getAnnotation(AwesomeCommand.class));
    }

}
