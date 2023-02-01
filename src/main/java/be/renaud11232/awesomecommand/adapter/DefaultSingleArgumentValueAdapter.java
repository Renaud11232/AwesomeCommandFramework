package be.renaud11232.awesomecommand.adapter;

import org.bukkit.entity.Player;

import java.lang.reflect.Type;

/**
 * The {@link DefaultSingleArgumentValueAdapter} class is used to transform a single {@link String} argument before
 * assigning it to a compatible {@link Type}
 */
public final class DefaultSingleArgumentValueAdapter implements SingleArgumentValueAdapter<Object> {

    private final StringValueAdapter stringValueAdapter = new StringValueAdapter();
    private final EnumValueAdapter enumValueAdapter = new EnumValueAdapter();
    private final ByteValueAdapter byteValueAdapter = new ByteValueAdapter();
    private final CharValueAdapter charValueAdapter = new CharValueAdapter();
    private final ShortValueAdapter shortValueAdapter = new ShortValueAdapter();
    private final IntValueAdapter intValueAdapter = new IntValueAdapter();
    private final LongValueAdapter longValueAdapter = new LongValueAdapter();
    private final FloatValueAdapter floatValueAdapter = new FloatValueAdapter();
    private final DoubleValueAdapter doubleValueAdapter = new DoubleValueAdapter();
    private final BooleanValueAdapter booleanValueAdapter = new BooleanValueAdapter();
    private final PlayerValueAdapter playerValueAdapter = new PlayerValueAdapter();

    /**
     * Converts the provided {@link String} value to a compatible {@link Type}.
     *
     * @param type  the target {@link Type} that the converted value will be assigned to
     * @param value {@link String} value to convert
     * @return the converted value
     * @throws UnsupportedTypeAdapterException if this {@link ArgumentValueAdapter} cannot convert the arguments to the desired {@link Type}
     * @throws IllegalArgumentException        if the value was not convertible to the desired {@link Type}
     * @see StringValueAdapter#apply(Type, String)
     * @see EnumValueAdapter#apply(Type, String)
     * @see ByteValueAdapter#apply(Type, String)
     * @see CharValueAdapter#apply(Type, String)
     * @see ShortValueAdapter#apply(Type, String)
     * @see IntValueAdapter#apply(Type, String)
     * @see LongValueAdapter#apply(Type, String)
     * @see FloatValueAdapter#apply(Type, String)
     * @see DoubleValueAdapter#apply(Type, String)
     * @see BooleanValueAdapter#apply(Type, String)
     * @see PlayerValueAdapter#apply(Type, String)
     */
    @Override
    public Object apply(Type type, String value) throws UnsupportedTypeAdapterException, IllegalArgumentException {
        Class<?> targetClass = (Class<?>) type;
        if (targetClass.isAssignableFrom(String.class)) {
            return stringValueAdapter.apply(type, value);
        } else if (targetClass.isEnum()) {
            return enumValueAdapter.apply(type, value);
        } else if (targetClass.isAssignableFrom(byte.class) || targetClass.isAssignableFrom(Byte.class)) {
            return byteValueAdapter.apply(type, value);
        } else if (targetClass.isAssignableFrom(char.class) || targetClass.isAssignableFrom(Character.class)) {
            return charValueAdapter.apply(type, value);
        } else if (targetClass.isAssignableFrom(short.class) || targetClass.isAssignableFrom(Short.class)) {
            return shortValueAdapter.apply(type, value);
        } else if (targetClass.isAssignableFrom(int.class) || targetClass.isAssignableFrom(Integer.class)) {
            return intValueAdapter.apply(type, value);
        } else if (targetClass.isAssignableFrom(long.class) || targetClass.isAssignableFrom(Long.class)) {
            return longValueAdapter.apply(type, value);
        } else if (targetClass.isAssignableFrom(float.class) || targetClass.isAssignableFrom(Float.class)) {
            return floatValueAdapter.apply(type, value);
        } else if (targetClass.isAssignableFrom(double.class) || targetClass.isAssignableFrom(Double.class)) {
            return doubleValueAdapter.apply(type, value);
        } else if (targetClass.isAssignableFrom(boolean.class) || targetClass.isAssignableFrom(Boolean.class)) {
            return booleanValueAdapter.apply(type, value);
        } else if (targetClass.isAssignableFrom(Player.class)) {
            return playerValueAdapter.apply(type, value);
        }
        throw new UnsupportedTypeAdapterException("Unable to convert argument to type " + targetClass.getName());
    }

}
