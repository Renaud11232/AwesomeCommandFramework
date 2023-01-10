package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.Field;

/**
 * The {@link CharValueAdapter} class is used to transform the {@link String} arguments before assigning them to a
 * {@link Field} of {@link Character} compatible type
 */
public class CharValueAdapter implements ArgumentValueAdapter<Character> {

    /**
     * Converts the provided {@link String} value to a {@link Character} in order to assign to a provided {@link Field}
     *
     * @param value the {@link String} value to convert
     * @param field the {@link Field} that the converted value will be assigned to
     * @return the converted {@link Character}
     * @throws IllegalArgumentException if the string does not contain exactly one character
     */
    @Override
    public Character apply(String value, Field field) {
        if (value.length() != 1) {
            throw new IllegalArgumentException("Argument value '" + value + "' could not be converted into character type");
        }
        return value.charAt(0);
    }
}
