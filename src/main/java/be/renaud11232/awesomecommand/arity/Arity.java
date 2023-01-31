package be.renaud11232.awesomecommand.arity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Arity {

    private static final Pattern ARITY_PATTERN = Pattern.compile("^(0|[1-9]\\d*)(?:\\.\\.([1-9]\\d*|\\*))?$");
    private final int min;
    private final Integer max;

    public Arity(String arity) {
        Matcher matcher = ARITY_PATTERN.matcher(arity);
        if (!matcher.find()) {
            throw new InvalidArityException("The provided string '" + arity + "' is not a valid arity");
        }
        min = Integer.parseInt(matcher.group(1));
        if (min < 0) {
            throw new InvalidArityException("The provided minimum cannot be negative and was " + min);
        }
        if (matcher.group(2) != null) {
            String maxStr = matcher.group(2);
            if (maxStr.equals("*")) {
                max = null;
            } else {
                max = Integer.parseInt(matcher.group(2));
                if (max < min) {
                    throw new InvalidArityException("The provided maximum cannot be less than the provided minimum : min = " + min + ", max = " + max);
                }
            }
        } else {
            max = min;
        }
    }

    public boolean hasMax() {
        return max != null;
    }

    public int getMinimum() {
        return min;
    }

    public int getMaximum() {
        if (!hasMax()) {
            throw new IllegalStateException("Cannot get the maximum for this arity, not maximum was set");
        }
        return max;
    }

    public boolean isFixed() {
        return hasMax() && max == min;
    }

}
