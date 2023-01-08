package be.renaud11232.awesomecommand.annotation.args;

import be.renaud11232.awesomecommand.adapter.ArgumentValueAdapter;
import be.renaud11232.awesomecommand.adapter.DefaultArgumentValueAdapter;
import be.renaud11232.awesomecommand.util.Constants;

import java.lang.annotation.*;

/**
 * Annotation used to describe a field containing the value of a positional command argument.
 * The {@link String} value of the argument will then be converted using the {@link PositionalArgument#adapter} is needed.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PositionalArgument {

    /**
     * The position of the argument in the argument list
     *
     * @return the argument position
     */
    int position();

    /**
     * The required property of the argument.
     * All required arguments <em>must</em> be positioned before the first optional (non required) argument.
     *
     * @return if the argument is required or not
     */
    boolean required() default true;

    /**
     * The default value to use if the argument was not provided.
     * If required, the corresponding {@link ArgumentValueAdapter} will be used to convert the argument to the correct
     * type.
     *
     * @return the default value
     */
    String defaultValue() default Constants.NO_VALUE;

    /**
     * The {@link ArgumentValueAdapter} subclass that will be used to transform the {@link String} arguments to the desired field type.
     * By default, the {@link DefaultArgumentValueAdapter} class will be used
     *
     * @return the {@link ArgumentValueAdapter} to use for the type conversion of the command arguments
     */
    Class<? extends ArgumentValueAdapter> adapter() default DefaultArgumentValueAdapter.class;
}
