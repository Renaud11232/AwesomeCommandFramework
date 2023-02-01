package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.Type;
import java.util.List;


/**
 * The {@link SingleArgumentValueAdapter} is used to transform a single {@link String} argument to a compatible
 * {@link Type}
 */
public interface SingleArgumentValueAdapter<T> extends ArgumentValueAdapter<T> {

    /**
     * Converts the provided singleton {@link List} of {@link String} values to an {@link Object} that can be assigned
     * to a given {@link Type}.
     *
     * @param type   the target {@link Type} that the converted values will be assigned to
     * @param values the {@link List} of {@link String} values to convert
     * @return the converted {@link Object}
     * @throws UnsupportedTypeAdapterException if this {@link ArgumentValueAdapter} cannot convert the arguments to the desired {@link Type}
     * @throws IllegalArgumentException        if the value was not convertible to the desired {@link Type} or if the provided {@link List} does not contain a single element
     */
    @Override
    default T apply(Type type, List<String> values) throws UnsupportedTypeAdapterException, IllegalArgumentException {
        if (values.size() != 1) {
            throw new IllegalArgumentException("Unable to convert argument list of size " + values.size() + " to single value");
        }
        return apply(type, values.get(0));
    }

    /**
     * Converts the provided {@link String} value to an {@link Object} that can be assigned to a given {@link Type}.
     *
     * @param type  the target {@link Type} that the converted value will be assigned to
     * @param value {@link String} value to convert
     * @return the converted {@link Object}
     * @throws UnsupportedTypeAdapterException if this {@link ArgumentValueAdapter} cannot convert the argument to the desired {@link Type}
     * @throws IllegalArgumentException        if the value was not convertible to the desired {@link Type}
     */
    T apply(Type type, String value) throws UnsupportedTypeAdapterException, IllegalArgumentException;

}
