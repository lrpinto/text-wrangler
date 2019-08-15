package org.luisa.miniwrangler.java.validation.utils;

import java.math.BigDecimal;
import java.text.MessageFormat;

public class BigDecimalUtils {

    public static String checkMinValue(String field, BigDecimal d, BigDecimal min) {
        final StringBuilder sb = new StringBuilder();
        if (d == null || d.compareTo(min) == -1) {
            final String msg = MessageFormat.format(ErrorMessage.MIN_VALUE, min);
            sb.append(field).append(" ").append(msg);
        }
        return sb.toString();
    }
}
