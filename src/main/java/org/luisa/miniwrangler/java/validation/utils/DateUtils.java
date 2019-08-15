package org.luisa.miniwrangler.java.validation.utils;

import java.sql.Date;
import java.time.Instant;

/**
 * Utility class to validate dates and build messages describing the errors (if
 * any)
 *
 * @author Luisa Pinto
 *
 */
public class DateUtils {

    /**
     * Check whether the given date is null
     *
     * @param field the name of field that this string is for
     * @param date  the given date
     * @return an empty string is the check has passed, or a message describing the
     *         error
     */
    public static String checkNotNull(String field, Date date) {
        final StringBuilder sb = new StringBuilder();
        if (date == null) {
            sb.append(field).append(" ").append(ErrorMessage.NOT_NULL);
        }
        return sb.toString();
    }

    /**
     * Check whether the given date is in the past or present
     *
     * @param field the name of field that this string is for
     * @param date  the given date
     * @return an empty string is the check has passed, or a message describing the
     *         error
     */
    public static String checkPastOrPresent(String field, Date date) {
        final StringBuilder sb = new StringBuilder();
        final long nowMilli = Instant.now().toEpochMilli();
        final Date now = new Date(nowMilli);
        if (date != null && date.after(now)) {
            sb.append(field).append(" ").append(ErrorMessage.PAST_OR_PRESENT);
        }
        return sb.toString();
    }
}
