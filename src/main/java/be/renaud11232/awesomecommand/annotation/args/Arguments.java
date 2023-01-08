package be.renaud11232.awesomecommand.annotation.args;

import java.lang.annotation.*;

/**
 * Annotation used to describe a field containing the arguments of the executed command.
 * The field must be an array of {@link String} or a {@link java.util.List} of {@link String} (or compatible).
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Arguments {
}
