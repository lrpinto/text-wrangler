package org.luisa.miniwrangler.java.exception;

import java.text.MessageFormat;

/**
 * Thrown to indicate that a specified does not exist
 *
 * @author Luisa Pinto
 *
 */
public class UnknownFieldException extends Exception {

    private static final long serialVersionUID = 1L;
    private final String field;
    private final String msg = "Unknown field {0}";

    /**
     * Constructs an UnknownFieldException with the given field
     *
     * @param field the given source field
     */
    public UnknownFieldException(String field) {
        this.field = field;
    }

    /*
     * (non-javadoc)
     *
     * @see java.lang.Exception
     */
    @Override
    public String getMessage() {
        return MessageFormat.format(msg, field);
    }

}
