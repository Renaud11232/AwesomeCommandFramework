package be.renaud11232.awesomecommand.annotation.args;

import java.lang.annotation.*;

/**
 * Annotation used to describe a field containing the alias of the executed command.
 * If this annotation is put on a subcommand field, then the field will be populated with the alias of the sub command only.
 * The field must be a {@link String}.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AliasParameter {
}
