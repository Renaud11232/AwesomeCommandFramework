package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.Field;

public class ByteValueAdapter implements ArgumentValueAdapter<Byte> {
    @Override
    public Byte apply(String value, Field field) {
        return Byte.parseByte(value);
    }
}
