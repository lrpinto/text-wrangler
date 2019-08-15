package org.luisa.miniwrangler.java.validation.utils;

import java.text.MessageFormat;

public class StringUtils {

	public static String checkLength(String field, String s, int min, int max) {
		final StringBuilder sb = new StringBuilder();
		if (s == null || s.length() < min || s.length() > max) {
			final String msg = MessageFormat.format(ErrorMessage.LENGTH_BETWEEN, min, max);
			sb.append(field).append(" ").append(msg);
		}
		return sb.toString();
	}

	public static String checkNotBlank(String field, String s) {
		final StringBuilder sb = new StringBuilder();
		if (s == null || s.isBlank()) {
			sb.append(field).append(" ").append(ErrorMessage.NOT_BLANK);
		}
		return sb.toString();
	}

}
