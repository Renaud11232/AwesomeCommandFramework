package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.Field;

/**
 * The {@link DefaultArgumentValueAdapter} class is used to transform a {@link String} argument to the most common types
 * This includes all primitive and primitive wrapper types, as well as {@link Enum} types.
 */
public final class DefaultArgumentValueAdapter implements ArgumentValueAdapter {

    /**
     * {@inheritDoc}
     */
    @Override
    public Object apply(String value, Field field) throws UnsupportedTypeAdapterException {
        Class<?> fieldType = field.getType();
        if (fieldType.isAssignableFrom(byte.class) || fieldType.isAssignableFrom(Byte.class)) {
            return Byte.parseByte(value);
        } else if (fieldType.isAssignableFrom(char.class) || fieldType.isAssignableFrom(Character.class)) {
            return parseChar(value);
        } else if (fieldType.isAssignableFrom(short.class) || fieldType.isAssignableFrom(Short.class)) {
            return Short.parseShort(value);
        } else if (fieldType.isAssignableFrom(int.class) || fieldType.isAssignableFrom(Integer.class)) {
            return Integer.parseInt(value);
        } else if (fieldType.isAssignableFrom(long.class) || fieldType.isAssignableFrom(Long.class)) {
            return Long.parseLong(value);
        } else if (fieldType.isAssignableFrom(float.class) || fieldType.isAssignableFrom(Float.class)) {
            return Float.parseFloat(value);
        } else if (fieldType.isAssignableFrom(double.class) || fieldType.isAssignableFrom(Double.class)) {
            return Double.parseDouble(value);
        } else if (fieldType.isAssignableFrom(boolean.class) || fieldType.isAssignableFrom(Boolean.class)) {
            return Boolean.parseBoolean(value);
        } else if (fieldType.isEnum()) {
            return parseEnum(value, fieldType);
        }
        throw new UnsupportedTypeAdapterException("Unable to convert argument to type " + field.getType().getName());
    }

    private Character parseChar(String value) {
        if (value.length() != 1) {
            throw new IllegalArgumentException("Argument value '" + value + "' could not be converted into character type");
        }
        return value.charAt(0);
    }

    @SuppressWarnings("unchecked,rawtypes")
    private Enum<?> parseEnum(String value, Class<?> enumClass) {
        return Enum.valueOf((Class<? extends Enum>) enumClass, value);
    }

}
