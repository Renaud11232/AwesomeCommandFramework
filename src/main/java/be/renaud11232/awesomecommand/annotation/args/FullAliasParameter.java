package be.renaud11232.awesomecommand.annotation.args;

import java.lang.annotation.*;

/**
 * Annotation used to describe a field containing all the aliases of the currently executed command chain in the order of which they appeared.
 * The field must be an array of {@link String}.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FullAliasParameter {
}
