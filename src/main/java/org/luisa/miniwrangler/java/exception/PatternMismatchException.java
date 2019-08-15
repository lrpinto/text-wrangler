package org.luisa.miniwrangler.java.exception;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.regex.Matcher;

/**
 * Thrown to indicate that a value does not match a specified pattern
 *
 * @author Luisa Pinto
 *
 */
public class PatternMismatchException extends Exception {

    private static final long serialVersionUID = 1L;
    private final String srcField;
    private final String srcFieldValue;
    private final Matcher matcher;
    private final String msg = "Pattern {0} mismatches {1} for {2} with value {3}";

    /**
     * Constructs an PatternMismatchException with the given source field, source
     * field value an matcher.
     *
     * @param srcField      the given source field
     * @param srcFieldValue the given source field value
     * @param matcher       the given matcher
     */
    public PatternMismatchException(String srcField, String srcFieldValue, Matcher matcher) {
        this.srcField = srcField;
        this.srcFieldValue = srcFieldValue;
        this.matcher = matcher;
    }

    /*
     * (non-javadoc)
     *
     * @see java.lang.Exception
     */
    @Override
    public String getMessage() {
        return MessageFormat.format(msg,
                matcher.pattern().toString(),
                Arrays.toString(matcher.replaceAll("").toCharArray()),
                srcField,
                srcFieldValue);
    }
}
