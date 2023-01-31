package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.Type;
import java.util.List;

public interface SingleArgumentValueAdapter<T> extends ArgumentValueAdapter<T> {

    @Override
    default T apply(Type type, List<String> values) throws UnsupportedTypeAdapterException, IllegalArgumentException {
        if (values.size() != 1) {
            throw new IllegalArgumentException("Unable to convert argument list of size " + values.size() + " to single value");
        }
        return apply(type, values.get(0));
    }

    T apply(Type type, String value) throws UnsupportedTypeAdapterException, IllegalArgumentException;

}
