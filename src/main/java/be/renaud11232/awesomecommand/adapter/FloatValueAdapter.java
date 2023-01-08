package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.Field;

public class FloatValueAdapter implements ArgumentValueAdapter<Float> {
    @Override
    public Float apply(String value, Field field) {
        return Float.parseFloat(value);
    }
}
