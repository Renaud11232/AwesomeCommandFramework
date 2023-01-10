package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.Field;

/**
 * The {@link ShortValueAdapter} class is used to transform the {@link String} arguments before assigning them to a
 * {@link Field} of {@link Short} compatible type
 */
public class ShortValueAdapter implements ArgumentValueAdapter<Short> {

    /**
     * Converts the provided {@link String} value to a {@link Short} in order to assign to a provided {@link Field}
     *
     * @param value the {@link String} value to convert
     * @param field the {@link Field} that the converted value will be assigned to
     * @return the converted {@link Short}
     * @throws NumberFormatException if the string does not contain a parsable {@link Short}
     */
    @Override
    public Short apply(String value, Field field) throws NumberFormatException {
        return Short.parseShort(value);
    }
}
