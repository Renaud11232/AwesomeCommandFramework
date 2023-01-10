package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.Field;

/**
 * The {@link EnumValueAdapter} class is used to transform the {@link String} arguments before assigning them to a
 * {@link Field} of {@link Enum} type.
 */
public class EnumValueAdapter implements ArgumentValueAdapter<Enum<?>> {

    /**
     * Converts the provided {@link String} value to an {@link Enum} value in order to assign to a provided {@link Field}
     *
     * @param value the {@link String} value to convert
     * @param field the {@link Field} that the converted value will be assigned to
     * @return the converted {@link Enum} value
     * @throws IllegalArgumentException if the enum type of the {@link Field} has no constant with the specified name, or the specified class object does not represent an enum type
     */
    @Override
    @SuppressWarnings("unchecked,rawtypes")
    public Enum<?> apply(String value, Field field) throws IllegalArgumentException {
        return Enum.valueOf((Class<? extends Enum>) field.getType(), value);
    }
}
