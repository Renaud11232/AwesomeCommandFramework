package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.Field;

/**
 * The {@link DoubleValueAdapter} class is used to transform the {@link String} arguments before assigning them to a
 * {@link Field} of {@link Double} compatible type
 */
public class DoubleValueAdapter implements ArgumentValueAdapter<Double> {

    /**
     * Converts the provided {@link String} value to a {@link Double} in order to assign to a provided {@link Field}
     *
     * @param value the {@link String} value to convert
     * @param field the {@link Field} that the converted value will be assigned to
     * @return the converted {@link Double}
     * @throws NumberFormatException if the string does not contain a parsable {@link Double}
     * @throws NullPointerException  if the value is null
     */
    @Override
    public Double apply(String value, Field field) throws NumberFormatException, NullPointerException {
        return Double.parseDouble(value);
    }
}
