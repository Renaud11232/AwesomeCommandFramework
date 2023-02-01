package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.Type;

/**
 * The {@link DoubleValueAdapter} class is used to transform a single {@link String} argument before assigning it to a
 * {@link Double} compatible {@link Type}.
 */
public class DoubleValueAdapter implements SingleArgumentValueAdapter<Double> {

    /**
     * Converts the provided {@link String} value to a {@link Double}.
     *
     * @param type  the target {@link Type} that the converted value will be assigned to
     * @param value the {@link String} value to convert
     * @return the converted {@link Double}
     * @throws NumberFormatException if the string does not contain a parsable {@link Double}
     * @throws NullPointerException  if the value is null
     * @see Double#parseDouble(String)
     */
    @Override
    public Double apply(Type type, String value) throws NumberFormatException, NullPointerException {
        return Double.parseDouble(value);
    }
}
