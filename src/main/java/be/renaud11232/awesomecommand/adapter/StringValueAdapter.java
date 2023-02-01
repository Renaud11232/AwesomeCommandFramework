package be.renaud11232.awesomecommand.adapter;

import java.lang.reflect.Type;

/**
 * The {@link StringValueAdapter} class is used to transform a single {@link String} argument before assigning it to a
 * {@link String} compatible {@link Type}.
 */
public class StringValueAdapter implements SingleArgumentValueAdapter<String> {

    /**
     * Returns the exact same {@link String} unchanged.
     *
     * @param type  the target {@link Type} that the converted value will be assigned to
     * @param value {@link String} value to convert
     * @return the exact same {@link String} as the <code>value</code> parameter
     */
    @Override
    public String apply(Type type, String value) {
        return value;
    }
}
