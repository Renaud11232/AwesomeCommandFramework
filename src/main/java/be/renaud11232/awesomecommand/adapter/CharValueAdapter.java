package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.Type;

/**
 * The {@link CharValueAdapter} class is used to transform a single {@link String} argument before assigning it to a
 * {@link Character} compatible {@link Type}.
 */
public class CharValueAdapter implements SingleArgumentValueAdapter<Character> {

    /**
     * Converts the provided {@link String} value to a {@link Character}.
     *
     * @param type  the target {@link Type} that the converted value will be assigned to
     * @param value the {@link String} value to convert
     * @return the converted {@link Character}
     * @throws IllegalArgumentException if the string does not contain exactly one character
     */
    @Override
    public Character apply(Type type, String value) {
        if (value.length() != 1) {
            throw new IllegalArgumentException("Argument value '" + value + "' could not be converted into Character type");
        }
        return value.charAt(0);
    }
}
