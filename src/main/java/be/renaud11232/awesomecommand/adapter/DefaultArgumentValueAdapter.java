package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * The {@link DefaultArgumentValueAdapter} class is used to transform a {@link List} of {@link String} arguments before
 * assigning them to a given {@link Type}.
 */
public final class DefaultArgumentValueAdapter implements ArgumentValueAdapter<Object> {

    private final ListValueAdapter listValueAdapter = new ListValueAdapter();
    private final ArrayValueAdapter arrayValueAdapter = new ArrayValueAdapter();
    private final DefaultSingleArgumentValueAdapter defaultSingleArgumentValueAdapter = new DefaultSingleArgumentValueAdapter();

    /**
     * Converts the provided {@link List} of {@link String} values to a compatible {@link Type}.
     * Depending on the given {@link Type}, the values will be converted to an array, {@link List}, or single value
     *
     * @param type   the target {@link Type} that the converted values will be assigned to
     * @param values the {@link List} of {@link String} values to convert
     * @return the converted value
     * @throws UnsupportedTypeAdapterException if this {@link ArgumentValueAdapter} cannot convert the arguments to the desired {@link Type}
     * @throws IllegalArgumentException        if the value was not convertible to the desired {@link Type}
     * @see ListValueAdapter#apply(Type, List)
     * @see ArrayValueAdapter#apply(Type, List)
     * @see DefaultSingleArgumentValueAdapter#apply(Type, List)
     */
    @Override
    public Object apply(Type type, List<String> values) throws UnsupportedTypeAdapterException, NumberFormatException {
        if (isList(type)) {
            return listValueAdapter.apply(type, values);
        } else if (type instanceof Class) {
            Class<?> classType = (Class<?>) type;
            if (classType.isArray()) {
                return arrayValueAdapter.apply(type, values);
            } else {
                return defaultSingleArgumentValueAdapter.apply(type, values);
            }
        }
        throw new UnsupportedTypeAdapterException("Unable to convert argument to type " + type.getTypeName());
    }

    private boolean isList(Type type) {
        if (!(type instanceof ParameterizedType)) {
            return false;
        }
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type rawType = parameterizedType.getRawType();
        if (!(rawType instanceof Class)) {
            return false;
        }
        Class<?> rawClass = (Class<?>) rawType;
        return rawClass.isAssignableFrom(List.class);
    }

}
