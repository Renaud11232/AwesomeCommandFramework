package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListValueAdapter implements ArgumentValueAdapter<List<?>> {

    private final DefaultSingleArgumentValueAdapter defaultSingleArgumentValueAdapter = new DefaultSingleArgumentValueAdapter();

    @Override
    public List<?> apply(Type type, List<String> values) throws UnsupportedTypeAdapterException, IllegalArgumentException {
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type actualType = parameterizedType.getActualTypeArguments()[0];
        List<Object> list = new ArrayList<>(values.size());
        for (int i = 0; i < values.size(); i++) {
            list.add(i, defaultSingleArgumentValueAdapter.apply(actualType, values.get(i)));
        }
        return list;
    }
}
