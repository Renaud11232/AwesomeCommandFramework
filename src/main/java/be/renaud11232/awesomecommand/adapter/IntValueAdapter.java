package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.Field;

public class IntValueAdapter implements ArgumentValueAdapter<Integer> {
    @Override
    public Integer apply(String value, Field field) {
        return Integer.parseInt(value);
    }
}
