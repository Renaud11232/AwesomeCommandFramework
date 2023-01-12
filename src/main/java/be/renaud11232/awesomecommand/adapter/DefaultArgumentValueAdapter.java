package be.renaud11232.awesomecommand.adapter;

import org.bukkit.entity.Player;

import java.lang.reflect.Field;

/**
 * The {@link DefaultArgumentValueAdapter} class is used to transform a {@link String} argument to the most common types
 * This includes all primitive and primitive wrapper types, as well as {@link Enum} types and bukkit's
 * {@link org.bukkit.entity.Player} type.
 */
public final class DefaultArgumentValueAdapter implements ArgumentValueAdapter<Object> {

    /**
     * Converts the provided {@link String} value to an {@link Object} in order to assign to a provided {@link Field}.
     * Depending on the type of the provided field, this method might return a {@link Byte}, {@link Character},
     * {@link Short}, {@link Integer}, {@link Long}, {@link Float}, {@link Double}, {@link Boolean}, {@link Player}
     * or {@link Enum}
     *
     * @param value the {@link String} value to convert
     * @param field the {@link Field} that the converted value will be assigned to
     * @return the converted {@link Object}
     * @throws NumberFormatException           if the string does not contain a parsable {@link Number} depending on the {@link Field} type
     * @throws NullPointerException            if the value is null
     * @throws UnsupportedTypeAdapterException if the type of the {@link Field} is incompatible with all supported types.
     * @throws IllegalArgumentException        if the string cannot be parsed to the desired {@link Field} type
     * @see EnumValueAdapter#apply(String, Field)
     * @see ByteValueAdapter#apply(String, Field)
     * @see CharValueAdapter#apply(String, Field)
     * @see ShortValueAdapter#apply(String, Field)
     * @see ShortValueAdapter#apply(String, Field)
     * @see IntValueAdapter#apply(String, Field)
     * @see LongValueAdapter#apply(String, Field)
     * @see FloatValueAdapter#apply(String, Field)
     * @see DoubleValueAdapter#apply(String, Field)
     * @see BooleanValueAdapter#apply(String, Field)
     * @see PlayerValueAdapter#apply(String, Field)
     */
    @Override
    public Object apply(String value, Field field) throws UnsupportedTypeAdapterException, NumberFormatException, NullPointerException {
        Class<?> fieldType = field.getType();
        if (fieldType.isAssignableFrom(String.class)) {
            return value;
        } else if (fieldType.isEnum()) {
            return new EnumValueAdapter().apply(value, field);
        } else if (fieldType.isAssignableFrom(byte.class) || fieldType.isAssignableFrom(Byte.class)) {
            return new ByteValueAdapter().apply(value, field);
        } else if (fieldType.isAssignableFrom(char.class) || fieldType.isAssignableFrom(Character.class)) {
            return new CharValueAdapter().apply(value, field);
        } else if (fieldType.isAssignableFrom(short.class) || fieldType.isAssignableFrom(Short.class)) {
            return new ShortValueAdapter().apply(value, field);
        } else if (fieldType.isAssignableFrom(int.class) || fieldType.isAssignableFrom(Integer.class)) {
            return new IntValueAdapter().apply(value, field);
        } else if (fieldType.isAssignableFrom(long.class) || fieldType.isAssignableFrom(Long.class)) {
            return new LongValueAdapter().apply(value, field);
        } else if (fieldType.isAssignableFrom(float.class) || fieldType.isAssignableFrom(Float.class)) {
            return new FloatValueAdapter().apply(value, field);
        } else if (fieldType.isAssignableFrom(double.class) || fieldType.isAssignableFrom(Double.class)) {
            return new DoubleValueAdapter().apply(value, field);
        } else if (fieldType.isAssignableFrom(boolean.class) || fieldType.isAssignableFrom(Boolean.class)) {
            return new BooleanValueAdapter().apply(value, field);
        } else if (fieldType.isAssignableFrom(Player.class)) {
            return new PlayerValueAdapter().apply(value, field);
        }
        throw new UnsupportedTypeAdapterException("Unable to convert argument to type " + field.getType().getName());
    }

}
