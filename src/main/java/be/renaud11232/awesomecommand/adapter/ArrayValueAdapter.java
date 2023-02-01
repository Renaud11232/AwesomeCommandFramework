package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.List;

/**
 * The {@link ArrayValueAdapter} class is used to transform a {@link List} of {@link String} arguments before
 * assigning them to an array compatible {@link Type}. The individual items will also be converted to match the
 * component {@link Type} of the array using {@link DefaultSingleArgumentValueAdapter}.
 */
public class ArrayValueAdapter implements ArgumentValueAdapter<Object> {

    private final DefaultSingleArgumentValueAdapter defaultSingleArgumentValueAdapter = new DefaultSingleArgumentValueAdapter();

    /**
     * Converts the provided {@link List} of {@link String} values to a compatible array.
     *
     * @param type   the target {@link Type} that the converted values will be assigned to
     * @param values the {@link List} of {@link String} values to convert
     * @return the converted array
     * @throws UnsupportedTypeAdapterException if this {@link ArgumentValueAdapter} cannot convert the arguments to the desired {@link Type}
     * @throws IllegalArgumentException        if the value was not convertible to the desired {@link Type}
     */
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
