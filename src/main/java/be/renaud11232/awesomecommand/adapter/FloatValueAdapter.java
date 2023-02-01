package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.Type;

/**
 * The {@link FloatValueAdapter} class is used to transform a single {@link String} argument before assigning it to a
 * {@link Float} compatible {@link Type}.
 */
public class FloatValueAdapter implements SingleArgumentValueAdapter<Float> {

    /**
     * Converts the provided {@link String} value to a {@link Float}.
     *
     * @param type  the target {@link Type} that the converted value will be assigned to
     * @param value the {@link String} value to convert
     * @return the converted {@link Float}
     * @throws NumberFormatException if the provided {@link String} does not contain a parsable {@link Float}
     * @throws NullPointerException  if the value is <code>null</code>
     * @see Float#parseFloat(String)
     */
    @Override
    public Float apply(Type type, String value) throws NumberFormatException, NullPointerException {
        return Float.parseFloat(value);
    }
}
