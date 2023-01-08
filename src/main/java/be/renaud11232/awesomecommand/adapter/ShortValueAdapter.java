package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.Field;

public class ShortValueAdapter implements ArgumentValueAdapter<Short> {
    @Override
    public Short apply(String value, Field field) {
        return Short.parseShort(value);
    }
}
