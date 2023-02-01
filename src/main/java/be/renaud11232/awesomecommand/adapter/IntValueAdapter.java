package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.Type;

/**
 * The {@link IntValueAdapter} class is used to transform a single {@link String} argument before assigning it to an
 * {@link Integer} compatible {@link Type}.
 */
public class IntValueAdapter implements SingleArgumentValueAdapter<Integer> {

    /**
     * Converts the provided {@link String} value to a {@link Integer}.
     *
     * @param type  the target {@link Type} that the converted value will be assigned to
     * @param value the {@link String} value to convert
     * @return the converted {@link Integer}
     * @throws NumberFormatException if the provided {@link String} does not contain a parsable {@link Integer}
     * @see Integer#parseInt(String)
     */
    @Override
    public Integer apply(Type type, String value) throws NumberFormatException {
        return Integer.parseInt(value);
    }
}
