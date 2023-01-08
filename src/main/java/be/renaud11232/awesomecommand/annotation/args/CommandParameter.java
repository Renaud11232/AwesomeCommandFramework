package be.renaud11232.awesomecommand.annotation.args;

import java.lang.annotation.*;

/**
 * Annotation used to describe a field containing the {@link org.bukkit.command.Command} object of the executed command.
 * If this annotation is put on a subcommand field, then command will be the subcommand.
 * The field must be a {@link org.bukkit.command.Command} (or compatible).
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CommandParameter {
}
