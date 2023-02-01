package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.Type;

/**
 * The {@link ByteValueAdapter} class is used to transform a single {@link String} argument before assigning it to a
 * {@link Byte} compatible {@link Type}.
 */
public class ByteValueAdapter implements SingleArgumentValueAdapter<Byte> {

    /**
     * Converts the provided {@link String} value to a {@link Byte}.
     *
     * @param type  the target {@link Type} that the converted value will be assigned to
     * @param value the {@link String} value to convert
     * @return the converted {@link Byte}
     * @throws NumberFormatException if the string does not contain a parsable {@link Byte}
     * @see Byte#parseByte(String)
     */
    @Override
    public Byte apply(Type type, String value) throws NumberFormatException {
        return Byte.parseByte(value);
    }
}
