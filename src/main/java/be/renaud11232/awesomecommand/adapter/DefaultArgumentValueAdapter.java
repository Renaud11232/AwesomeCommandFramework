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
     * {@inheritDoc}
     */
    @Override
    public Object apply(String value, Field field) throws UnsupportedTypeAdapterException {
        Class<?> fieldType = field.getType();
        if (fieldType.isEnum()) {
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
