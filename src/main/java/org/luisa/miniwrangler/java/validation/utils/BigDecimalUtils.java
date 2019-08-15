package org.luisa.miniwrangler.java.validation.utils;

import java.math.BigDecimal;
import java.text.MessageFormat;

/**
 * Utility class to validate big decimals and build messages describing the
 * errors (if any)
 *
 * @author Luisa Pinto
 *
 */
public class BigDecimalUtils {

    /**
     * Check whether the given BigDecimal is less or equal than the given min
     *
     * @param field the name of the field for which the value is d
     * @param d     the given big decimal
     * @param min   the given minimum
     * @return an empty string is the check has passed, or a message describing the
     *         error
     */
    public static String checkMinValue(String field, BigDecimal d, BigDecimal min) {
        final StringBuilder sb = new StringBuilder();
        if (d == null || d.compareTo(min) == -1) {
            final String msg = MessageFormat.format(ErrorMessage.MIN_VALUE, min);
            sb.append(field).append(" ").append(msg);
        }
        return sb.toString();
    }
}
