package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.Field;

/**
 * The {@link IntValueAdapter} class is used to transform the {@link String} arguments before assigning them to a
 * {@link Field} of {@link Integer} compatible type
 */
public class IntValueAdapter implements ArgumentValueAdapter<Integer> {

    /**
     * Converts the provided {@link String} value to a {@link Integer} in order to assign to a provided {@link Field}
     *
     * @param value the {@link String} value to convert
     * @param field the {@link Field} that the converted value will be assigned to
     * @return the converted {@link Integer}
     * @throws NumberFormatException if the string does not contain a parsable {@link Integer}
     */
    @Override
    public Integer apply(String value, Field field) throws NumberFormatException {
        return Integer.parseInt(value);
    }
}
