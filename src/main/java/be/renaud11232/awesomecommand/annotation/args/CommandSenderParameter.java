package be.renaud11232.awesomecommand.annotation.args;

import java.lang.annotation.*;

/**
 * Annotation used to describe a field containing the {@link org.bukkit.command.CommandSender} object of the executed command.
 * The field must be a {@link org.bukkit.command.CommandExecutor}.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CommandSenderParameter {
}
