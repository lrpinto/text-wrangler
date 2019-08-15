package org.luisa.miniwrangler.java.exception;

import java.text.MessageFormat;

/**
 * Thrown to indicate that a specified source field index does not exist
 *
 * @author Luisa Pinto
 *
 */
public class UnknownIndexException extends Exception {

    private static final long serialVersionUID = 1L;
    private final String field;
    private final String msg = "Unknown {0} index";

    /**
     * Constructs an UnknownIndexException with the given field
     *
     * @param field the given source field
     */
    public UnknownIndexException(String field) {
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
