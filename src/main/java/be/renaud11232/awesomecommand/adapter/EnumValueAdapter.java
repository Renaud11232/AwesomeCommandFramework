package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.Field;

public class EnumValueAdapter implements ArgumentValueAdapter<Enum<?>> {
    @Override
    @SuppressWarnings("unchecked,rawtypes")
    public Enum<?> apply(String value, Field field) {
        return Enum.valueOf((Class<? extends Enum>) field.getType(), value);
    }
}
