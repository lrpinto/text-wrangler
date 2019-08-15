package org.luisa.miniwrangler.java.data.utils;

/**
 * StringUtils.java
 *
 * A utility class to perform operations with Strings.
 *
 * @author Luisa Pinto
 *
 */
public class StringUtils {

    /**
     * Remove leading quotes ', " or ` from the given string
     *
     * @param s the given string
     * @return a new String without leading quotes
     */
    public static String stripQuotes(String s) {
        // Strip quotes if needed
        final char start = s.charAt(0);
        final char end = s.charAt(s.length() - 1);
        if (start == '\'' || start == '\"' || start == '`' && start == end) {
            s = s.substring(1, s.length() - 1);
        }
        return s;
    }

    /**
     * Convert a String to proper-case (also called title-case)
     *
     * @param s the given String
     * @return a new String without leading quotes
     */
    public static String toProperCase(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }
        final StringBuilder converted = new StringBuilder();
        boolean convertNext = true;
        for (char ch : s.toCharArray()) {
            if (Character.isSpaceChar(ch)) {
                convertNext = true;
            } else if (convertNext) {
                ch = Character.toTitleCase(ch);
                convertNext = false;
            } else {
                ch = Character.toLowerCase(ch);
            }
            converted.append(ch);
        }

        return converted.toString();

    }

}
