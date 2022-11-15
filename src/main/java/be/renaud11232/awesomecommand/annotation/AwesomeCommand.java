package be.renaud11232.awesomecommand.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AwesomeCommand {

    String name();

    String description() default "";

    String[] aliases() default {};

    String permission() default "";

    String permissionMessage() default "";

    String usage() default "";

    Class<?>[] subCommands() default {};

}
