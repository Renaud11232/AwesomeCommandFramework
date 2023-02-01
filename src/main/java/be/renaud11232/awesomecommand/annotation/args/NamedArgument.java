package be.renaud11232.awesomecommand.annotation.args;

import be.renaud11232.awesomecommand.adapter.ArgumentValueAdapter;
import be.renaud11232.awesomecommand.adapter.DefaultArgumentValueAdapter;
import be.renaud11232.awesomecommand.util.Constants;

import java.lang.annotation.*;

/**
 * Annotation used to describe a field containing the value of a named command argument. Named arguments must follow
 * the following format : <code>/command argument_name=value</code>. Where <code>=</code> can be any desired separator.
 * The {@link String} value of the argument will then be converted using the {@link NamedArgument#adapter} if needed.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NamedArgument {

    /**
     * The name of the argument
     *
     * @return the name of the argument
     */
    String name();

    /**
     * The other names of the same argument
     *
     * @return the argument aliases
     */
    String[] aliases() default {};

    /**
     * The default value to use if the argument was not provided.
     * If needed, the corresponding {@link NamedArgument#adapter} will be used to convert the argument to the correct
     * type. A special value of {@link Constants#NO_VALUE} is used to indicate no default value.
     *
     * @return the default value
     */
    String defaultValue() default Constants.NO_VALUE;

    /**
     * The separator used to differentiate the argument name from its value. Only the first occurrence will be
     * interpreted as the separator. Defaults to <code>=</code>
     *
     * @return the separator to use
     */
    String separator() default "=";


    /**
     * The {@link ArgumentValueAdapter} subclass that will be used to transform the {@link String} arguments value to
     * the desired field type. By default, the {@link DefaultArgumentValueAdapter} class will be used
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
