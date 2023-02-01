package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.Type;

/**
 * The {@link ShortValueAdapter} class is used to transform a single {@link String} argument before assigning it to a
 * {@link Short} compatible {@link Type}.
 */
public class ShortValueAdapter implements SingleArgumentValueAdapter<Short> {

    /**
     * Converts the provided {@link String} value to a {@link Short}.
     *
     * @param type  the target {@link Type} that the converted value will be assigned to
     * @param value the {@link String} value to convert
     * @return the converted {@link Short}
     * @throws NumberFormatException if the provided {@link String} does not contain a parsable {@link Short}
     * @see Short#parseShort(String)
     */
    @Override
    public Short apply(Type type, String value) throws NumberFormatException {
        return Short.parseShort(value);
    }
}
