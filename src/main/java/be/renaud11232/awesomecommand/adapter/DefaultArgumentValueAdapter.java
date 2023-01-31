package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public final class DefaultArgumentValueAdapter implements ArgumentValueAdapter<Object> {

    private final ListValueAdapter listValueAdapter = new ListValueAdapter();
    private final ArrayValueAdapter arrayValueAdapter = new ArrayValueAdapter();
    private final DefaultSingleArgumentValueAdapter defaultSingleArgumentValueAdapter = new DefaultSingleArgumentValueAdapter();

    @Override
    public Object apply(Type type, List<String> values) throws UnsupportedTypeAdapterException, NumberFormatException, NullPointerException {
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
