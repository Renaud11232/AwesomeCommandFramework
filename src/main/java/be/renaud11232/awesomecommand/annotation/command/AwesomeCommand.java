package be.renaud11232.awesomecommand.annotation.command;

import java.lang.annotation.*;

/**
 * Annotation used to describe a command specification
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AwesomeCommand {

    /**
     * The command name
     *
     * @return the name of the command
     */
    String name();

    /**
     * The command description
     *
     * @return the description of the command
     */
    String description() default "";

    /**
     * The command aliases
     *
     * @return the aliases of the command
     */
    String[] aliases() default {};

    /**
     * The command permission
     *
     * @return the permission of the command
     */
    String permission() default "";

    /**
     * The command permission message
     *
     * @return the message displayed when the command permission is denied
     */
    String permissionMessage() default "";

    /**
     * The command usage
     *
     * @return the usage of the command
     */
    String usage() default "";

    /**
     * The subcommands
     *
     * @return the subcommands of the command
     */
    Class<?>[] subCommands() default {};

}
