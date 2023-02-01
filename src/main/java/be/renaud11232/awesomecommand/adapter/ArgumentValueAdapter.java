package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.Type;
import java.util.List;

/**
 * The {@link ArgumentValueAdapter} is used to transform a {@link List} pf {@link String} arguments to a compatible
 * {@link Type}
 */
public interface ArgumentValueAdapter<T> {

    /**
     * Converts the provided {@link List} of {@link String} values to an {@link Object} that can be assigned to a given
     * {@link Type}
     *
     * @param type   the target {@link Type} that the converted values will be assigned to
     * @param values the {@link List} of {@link String} values to convert
     * @return the converted {@link Object}
     * @throws UnsupportedTypeAdapterException if this {@link ArgumentValueAdapter} cannot convert the arguments to the desired {@link Type}
     * @throws IllegalArgumentException        if the value was not convertible to the desired {@link Type}
     */
    T apply(Type type, List<String> values) throws UnsupportedTypeAdapterException, IllegalArgumentException;


}
