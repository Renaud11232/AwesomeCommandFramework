package be.renaud11232.awesomecommand;

import be.renaud11232.awesomecommand.annotation.AwesomeCommand;

import java.util.Objects;

public class Util {

    private Util() {
    }

    public static AwesomeCommand getCommandAnnotation(Class<?> commandClass) {
        return Objects.requireNonNull(commandClass.getAnnotation(be.renaud11232.awesomecommand.annotation.AwesomeCommand.class));
    }

}
