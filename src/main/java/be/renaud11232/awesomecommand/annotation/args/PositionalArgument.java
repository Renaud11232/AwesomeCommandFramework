package be.renaud11232.awesomecommand.annotation.args;

import be.renaud11232.awesomecommand.adapter.ArgumentValueAdapter;
import be.renaud11232.awesomecommand.adapter.DefaultArgumentValueAdapter;
import be.renaud11232.awesomecommand.util.Constants;

import java.lang.annotation.*;

/**
 * Annotation used to describe a field containing the value of a positional command argument.
 * The {@link String} value of the argument will then be converted using the {@link PositionalArgument#adapter} if needed.
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
     * The default value to use if the argument was not provided.
     * If required, the corresponding {@link PositionalArgument#adapter} will be used to convert the argument to the correct
     * type. A special value of {@link Constants#NO_VALUE} is used to indicate no default value.
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
    Class<? extends ArgumentValueAdapter<?>> adapter() default DefaultArgumentValueAdapter.class;

    /**
     * The {@link String} representation of the argument {@link be.renaud11232.awesomecommand.arity.Arity}
     *
     * @return the arity of the argument
     */
    String arity() default "1";
}
