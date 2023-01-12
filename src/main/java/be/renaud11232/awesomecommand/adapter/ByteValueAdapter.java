package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.Field;

/**
 * The {@link ByteValueAdapter} class is used to transform the {@link String} arguments before assigning them to a
 * {@link Field} of {@link Byte} compatible type
 */
public class ByteValueAdapter implements ArgumentValueAdapter<Byte> {

    /**
     * Converts the provided {@link String} value to a {@link Byte} in order to assign to a provided {@link Field}
     *
     * @param value the {@link String} value to convert
     * @param field the {@link Field} that the converted value will be assigned to
     * @return the converted {@link Byte}
     * @throws NumberFormatException if the string does not contain a parsable {@link Byte}
     * @see Byte#parseByte(String)
     */
    @Override
    public Byte apply(String value, Field field) throws NumberFormatException {
        return Byte.parseByte(value);
    }
}