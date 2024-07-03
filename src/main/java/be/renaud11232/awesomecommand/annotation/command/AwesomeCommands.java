package be.renaud11232.awesomecommand.annotation.command;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AwesomeCommands {

    Class<?>[] value() default {};

}
