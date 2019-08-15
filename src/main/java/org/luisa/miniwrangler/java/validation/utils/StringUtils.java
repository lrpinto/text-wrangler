package org.luisa.miniwrangler.java.validation.utils;

import java.text.MessageFormat;

/**
 * Utility class to validate strings and build messages describing the errors
 * (if any)
 *
 * @author Luisa Pinto
 *
 */
public class StringUtils {

    /**
     * Check whether the given string is between a min and max length
     *
     * @param field the name of field that this string is for
     * @param s     the given string
     * @param min   the min length
     * @param max   the max length
     * @return an empty string is the check has passed, or a message describing the
     *         error
     */
    public static String checkLength(String field, String s, int min, int max) {
        final StringBuilder sb = new StringBuilder();
        if (s == null || s.length() < min || s.length() > max) {
            final String msg = MessageFormat.format(ErrorMessage.LENGTH_BETWEEN, min, max);
            sb.append(field).append(" ").append(msg);
        }
        return sb.toString();
    }

    /**
     * Returns true if the string is non-null, not empty or does not contains only
     * white space codepoints,otherwise false.
     *
     * @param field the name of field that this string is for
     * @param s     the given string
     * @return an empty string is the check has passed, or a message describing the
     *         error
     */
    public static String checkNotBlank(String field, String s) {
        final StringBuilder sb = new StringBuilder();
        if (s == null || s.isBlank()) {
            sb.append(field).append(" ").append(ErrorMessage.NOT_BLANK);
        }
        return sb.toString();
    }

}
