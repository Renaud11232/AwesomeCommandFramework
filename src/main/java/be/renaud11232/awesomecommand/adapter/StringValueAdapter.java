package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.Type;

public class StringValueAdapter implements SingleArgumentValueAdapter<String> {
    @Override
    public String apply(Type type, String value) throws UnsupportedTypeAdapterException, IllegalArgumentException {
        return value;
    }
}
