package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.Field;

/**
 * The {@link FloatValueAdapter} class is used to transform the {@link String} arguments before assigning them to a
 * {@link Field} of {@link Float} compatible type
 */
public class FloatValueAdapter implements ArgumentValueAdapter<Float> {

    /**
     * Converts the provided {@link String} value to a {@link Float} in order to assign to a provided {@link Field}
     *
     * @param value the {@link String} value to convert
     * @param field the {@link Field} that the converted value will be assigned to
     * @return the converted {@link Float}
     * @throws NumberFormatException if the string does not contain a parsable {@link Float}
     * @throws NullPointerException  if the value is null
     * @see Float#parseFloat(String)
     */
    @Override
    public Float apply(String value, Field field) throws NumberFormatException, NullPointerException {
        return Float.parseFloat(value);
    }
}
