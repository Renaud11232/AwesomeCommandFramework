package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.Field;

/**
 * The {@link ArgumentValueAdapter} is used to transform the {@link String} arguments before assigning them to a {@link Field}
 * to a compatible type.
 */
public interface ArgumentValueAdapter {

    /**
     * Converts the provided {@link String} value to an {@link Object} of any Type in order to assign to a provided {@link Field}
     *
     * @param value the {@link String} value to convert
     * @param field the {@link Field} that the converted value will be assigned to
     * @return the converted object
     * @throws UnsupportedTypeAdapterException if this {@link ArgumentValueAdapter} cannot convert the argument to the desired type
     */
    Object apply(String value, Field field) throws UnsupportedTypeAdapterException;


}
