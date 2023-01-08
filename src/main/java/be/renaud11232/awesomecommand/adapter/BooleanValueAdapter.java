package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.Field;

public class BooleanValueAdapter implements ArgumentValueAdapter<Boolean> {
    @Override
    public Boolean apply(String value, Field field) {
        return Boolean.parseBoolean(value);
    }
}
