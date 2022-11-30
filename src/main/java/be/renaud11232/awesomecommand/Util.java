package be.renaud11232.awesomecommand;

import be.renaud11232.awesomecommand.annotation.command.AwesomeCommand;

import java.util.Objects;

public class Util {

    private Util() {
        throw new UnsupportedOperationException();
    }

    public static AwesomeCommand getCommandAnnotation(Class<?> commandClass) {
        return Objects.requireNonNull(commandClass.getAnnotation(AwesomeCommand.class));
    }

}
