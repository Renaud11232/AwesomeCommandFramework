package be.renaud11232.awesomecommand.arity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@link Arity} class represents a command argument arity, which tells how many times a given argument can/must
 * be repeated for it to be valid.
 */
public class Arity {

    private static final Pattern ARITY_PATTERN = Pattern.compile("^(0|[1-9]\\d*)(?:\\.\\.([1-9]\\d*|\\*))?$");
    private final int min;
    private final Integer max;

    /**
     * Constructs a new {@link Arity} based on a given string. If this {@link String} contains a single integer,
     * the arity is fixed and the minimum and maximum number of times the argument can be repeated are the same. If this
     * is a {@link String} following this format : <code>min..max</code>, where <code>min</code> is an integer and
     * <code>max</code> is either an integer or the string <code>*</code>. Then the minimum and maximum number of
     * arguments are set respectively. If <code>max</code> was <code>*</code>, the {@link Arity} does not have a
     * maximum number of arguments.
     *
     * @param arity the {@link String} used to construct this {@link Arity}
     * @throws InvalidArityException if the given {@link String} does not follow the right format, or if the provided
     *                               minimum was strictly greater than the maximum
     */
    public Arity(String arity) throws InvalidArityException {
        Matcher matcher = ARITY_PATTERN.matcher(arity);
        if (!matcher.find()) {
            throw new InvalidArityException("The provided string '" + arity + "' is not a valid arity");
        }
        min = Integer.parseInt(matcher.group(1));
        if (min < 0) {
            throw new InvalidArityException("The provided minimum cannot be negative and was " + min);
        }
        String maxStr = matcher.group(2);
        if (maxStr == null) {
            max = min;
        } else {
            if (maxStr.equals("*")) {
                max = null;
            } else {
                max = Integer.parseInt(matcher.group(2));
                if (max < min) {
                    throw new InvalidArityException("The provided maximum cannot be less than the provided minimum : min = " + min + ", max = " + max);
                }
            }
        }
    }

    /**
     * Tells whether this {@link Arity} does have a maximum
     *
     * @return <code>true</code> if the {@link Arity} does have a maximum, <code>false</code> otherwise.
     */
    public boolean hasMax() {
        return max != null;
    }

    /**
     * Returns the minimum of this {@link Arity}
     *
     * @return the minimum
     */
    public int getMinimum() {
        return min;
    }

    /**
     * Returns the maximum of this {@link Arity}, if it exists
     *
     * @return the maximum
     * @throws IllegalStateException if this {@link Arity} does not have a maximum
     */
    public int getMaximum() throws IllegalStateException {
        if (!hasMax()) {
            throw new IllegalStateException("Cannot get the maximum for this arity, not maximum was set");
        }
        return max;
    }

}
