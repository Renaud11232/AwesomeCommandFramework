package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@link ListValueAdapter} class is used to transform a {@link List} of {@link String} arguments before
 * assigning them to a {@link List} compatible {@link Type}. The individual items will also be converted to match the
 * {@link ParameterizedType} of the {@link List} using {@link DefaultSingleArgumentValueAdapter}.
 */
public class ListValueAdapter implements ArgumentValueAdapter<List<?>> {

    private final DefaultSingleArgumentValueAdapter defaultSingleArgumentValueAdapter = new DefaultSingleArgumentValueAdapter();

    /**
     * Converts the provided {@link List} of {@link String} values to a compatible {@link List}.
     *
     * @param type   the target {@link Type} that the converted values will be assigned to
     * @param values the {@link List} of {@link String} values to convert
     * @return the converted {@link List}
     * @throws UnsupportedTypeAdapterException if this {@link ArgumentValueAdapter} cannot convert the arguments to the desired {@link Type}
     * @throws IllegalArgumentException        if the value was not convertible to the desired {@link Type}
     */
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
