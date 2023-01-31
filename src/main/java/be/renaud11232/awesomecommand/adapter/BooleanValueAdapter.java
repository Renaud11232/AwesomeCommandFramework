package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * The {@link BooleanValueAdapter} class is used to transform the {@link String} arguments before assigning them to a
 * {@link Field} of {@link Boolean} compatible type
 */
public class BooleanValueAdapter implements SingleArgumentValueAdapter<Boolean> {

    /**
     * Converts the provided {@link String} value to a {@link Boolean} in order to assign to a provided {@link Field}
     *
     * @param value the {@link String} value to convert
     * @param field the {@link Field} that the converted value will be assigned to
     * @return the converted {@link Boolean}
     * @see Boolean#parseBoolean(String)
     */
    @Override
    public Boolean apply(Type type, String value) {
        return Boolean.parseBoolean(value);
    }
}
