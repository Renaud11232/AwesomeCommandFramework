package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * The {@link LongValueAdapter} class is used to transform the {@link String} arguments before assigning them to a
 * {@link Field} of {@link Long} compatible type
 */
public class LongValueAdapter implements SingleArgumentValueAdapter<Long> {

    /**
     * Converts the provided {@link String} value to a {@link Long} in order to assign to a provided {@link Field}
     *
     * @param value the {@link String} value to convert
     * @param field the {@link Field} that the converted value will be assigned to
     * @return the converted {@link Long}
     * @throws NumberFormatException if the string does not contain a parsable {@link Long}
     * @see Long#parseLong(String)
     */
    @Override
    public Long apply(Type type, String value) throws NumberFormatException {
        return Long.parseLong(value);
    }
}
