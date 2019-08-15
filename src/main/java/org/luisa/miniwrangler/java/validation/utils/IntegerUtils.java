package org.luisa.miniwrangler.java.validation.utils;

import java.text.MessageFormat;

/**
 * Utility class to validate integers and build messages describing the errors
 * (if any)
 *
 * @author Luisa Pinto
 *
 */
public class IntegerUtils {

    /**
     * Check whether the given integer is less or equal than the given min
     *
     * @param field the name of the field for which the value is n
     * @param n     the given integer
     * @param min   the given minimum
     * @return an empty string is the check has passed, or a message describing the
     *         error
     */
    public static String checkMinValue(String field, Integer n, int min) {
        final StringBuilder sb = new StringBuilder();
        if (n == null || n.intValue() < min) {
            final String msg = MessageFormat.format(ErrorMessage.MIN_VALUE, min);
            sb.append(field).append(" ").append(msg);
        }
        return sb.toString();
    }

    /**
     * Check whether the the given n is not null
     *
     * @param field the name of the field for which the value is n
     * @param n     the given integer
     * @return an empty string is the check has passed, or a message describing the
     *         error
     */
    public static String checkNotNull(String field, Integer n) {
        final StringBuilder sb = new StringBuilder();
        if (n == null) {
            sb.append(field).append(" ").append(ErrorMessage.NOT_NULL);
        }
        return sb.toString();
    }
}
