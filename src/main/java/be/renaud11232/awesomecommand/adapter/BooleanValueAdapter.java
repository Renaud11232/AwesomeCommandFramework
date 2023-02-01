package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.Type;

/**
 * The {@link BooleanValueAdapter} class is used to transform a single {@link String} argument before assigning it to a
 * {@link Boolean} compatible {@link Type}.
 */
public class BooleanValueAdapter implements SingleArgumentValueAdapter<Boolean> {

    /**
     * Converts the provided {@link String} value to a {@link Boolean}.
     *
     * @param type  the target {@link Type} that the converted value will be assigned to
     * @param value the {@link String} value to convert
     * @return the converted {@link Boolean}
     * @see Boolean#parseBoolean(String)
     */
    @Override
    public Boolean apply(Type type, String value) {
        return Boolean.parseBoolean(value);
    }
}
