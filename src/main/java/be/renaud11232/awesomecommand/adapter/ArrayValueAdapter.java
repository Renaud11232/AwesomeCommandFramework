package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.List;

public class ArrayValueAdapter implements ArgumentValueAdapter<Object> {

    private final DefaultSingleArgumentValueAdapter defaultSingleArgumentValueAdapter = new DefaultSingleArgumentValueAdapter();

    @Override
    public Object apply(Type type, List<String> values) throws UnsupportedTypeAdapterException, IllegalArgumentException {
        Class<?> classType = (Class<?>) type;
        Class<?> componentType = classType.getComponentType();
        Object array = Array.newInstance(componentType, values.size());
        for (int i = 0; i < values.size(); i++) {
            Array.set(array, i, defaultSingleArgumentValueAdapter.apply(componentType, values.get(i)));
        }
        return array;
    }
}
