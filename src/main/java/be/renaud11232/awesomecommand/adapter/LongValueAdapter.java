package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.Field;

public class LongValueAdapter implements ArgumentValueAdapter<Long> {
    @Override
    public Long apply(String value, Field field) {
        return Long.parseLong(value);
    }
}
