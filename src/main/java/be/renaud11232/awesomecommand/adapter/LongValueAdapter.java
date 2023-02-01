package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.Type;

/**
 * The {@link LongValueAdapter} class is used to transform a single {@link String} argument before assigning it to a
 * {@link Long} compatible {@link Type}.
 */
public class LongValueAdapter implements SingleArgumentValueAdapter<Long> {

    /**
     * Converts the provided {@link String} value to a {@link Long}.
     *
     * @param type  the target {@link Type} that the converted value will be assigned to
     * @param value the {@link String} value to convert
     * @return the converted {@link Long}
     * @throws NumberFormatException if the provided {@link String} does not contain a parsable {@link Long}
     * @see Long#parseLong(String)
     */
    @Override
    public Long apply(Type type, String value) throws NumberFormatException {
        return Long.parseLong(value);
    }
}
