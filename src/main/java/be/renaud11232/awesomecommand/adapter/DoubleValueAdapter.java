package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.Field;

public class DoubleValueAdapter implements ArgumentValueAdapter<Double> {
    @Override
    public Double apply(String value, Field field) {
        return Double.parseDouble(value);
    }
}
