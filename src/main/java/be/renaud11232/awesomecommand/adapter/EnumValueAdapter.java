package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.Type;

/**
 * The {@link EnumValueAdapter} class is used to transform a single {@link String} argument before assigning it to an
 * {@link Enum} {@link Type}.
 */
public class EnumValueAdapter implements SingleArgumentValueAdapter<Enum<?>> {

    /**
     * Converts the provided {@link String} value to an {@link Enum} value.
     *
     * @param type  the target {@link Type} that the converted value will be assigned to
     * @param value the {@link String} value to convert
     * @return the converted {@link Enum} value
     * @throws IllegalArgumentException if the enum {@link Type} has no constant with the specified name, or the specified {@link Type} object does not represent an {@link Enum} type
     * @see Enum#valueOf(Class, String)
     */
    @Override
    @SuppressWarnings("unchecked,rawtypes")
    public Enum<?> apply(Type type, String value) throws IllegalArgumentException {
        return Enum.valueOf((Class<? extends Enum>) type, value);
    }
}
