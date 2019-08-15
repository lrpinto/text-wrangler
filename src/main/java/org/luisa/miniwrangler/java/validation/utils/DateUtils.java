package org.luisa.miniwrangler.java.validation.utils;

import java.sql.Date;
import java.time.Instant;

public class DateUtils {

	public static String checkNotNull(String field, Date date) {
		final StringBuilder sb = new StringBuilder();
		if (date == null) {
			sb.append(field).append(" ").append(ErrorMessage.NOT_NULL);
		}
		return sb.toString();
	}

	public static String checkPastOrPresent(String field, Date orderDt) {
		final StringBuilder sb = new StringBuilder();
		final long nowMilli = Instant.now().toEpochMilli();
		final Date now = new Date(nowMilli);
		if (orderDt != null && orderDt.after(now)) {
			sb.append(field).append(" ").append(ErrorMessage.PAST_OR_PRESENT);
		}
		return sb.toString();
	}
}
