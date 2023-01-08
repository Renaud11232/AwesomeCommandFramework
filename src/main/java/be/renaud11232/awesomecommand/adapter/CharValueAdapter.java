package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.Field;

public class CharValueAdapter implements ArgumentValueAdapter<Character> {
    @Override
    public Character apply(String value, Field field) {
        if (value.length() != 1) {
            throw new IllegalArgumentException("Argument value '" + value + "' could not be converted into character type");
        }
        return value.charAt(0);
    }
}
