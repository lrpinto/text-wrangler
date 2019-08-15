package org.luisa.miniwrangler.java.validation.utils;

import java.math.BigDecimal;
import java.text.MessageFormat;

public class IntegerUtils {

	public static String checkMinValue(String field, BigDecimal d, BigDecimal min) {
		final StringBuilder sb = new StringBuilder();
		if (d == null || d.compareTo(min) > -1) {
			final String msg = MessageFormat.format(ErrorMessage.MIN_VALUE, min);
			sb.append(field).append(" ").append(msg);
		}
		return sb.toString();
	}

	public static String checkMinValue(String field, Integer n, int min) {
		final StringBuilder sb = new StringBuilder();
		if (n == null || n.intValue() < min) {
			final String msg = MessageFormat.format(ErrorMessage.MIN_VALUE, min);
			sb.append(field).append(" ").append(msg);
		}
		return sb.toString();
	}

	public static String checkNotNull(String field, Integer n) {
		final StringBuilder sb = new StringBuilder();
		if (n == null) {
			sb.append(field).append(" ").append(ErrorMessage.NOT_NULL);
		}
		return sb.toString();
	}
}
