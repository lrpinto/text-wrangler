package org.luisa.miniwrangler.java.exception;

import java.text.MessageFormat;

/**
 * Thrown to indicate that a specified does not exist
 *
 * @author Luisa Pinto
 *
 */
public class InvalidDataException extends Exception {

    private static final long serialVersionUID = 1L;
    private final String field;
    private final String data;
    private final String msg = "Invalid data '{0}' for field '{1}'";

    /**
     * Constructs an UnknownFieldException with the given field
     *
     * @param field the given source field
     */
    public InvalidDataException(String field, String data) {
        this.field = field;
        this.data = data;
    }

    /*
     * (non-javadoc)
     *
     * @see java.lang.Exception
     */
    @Override
    public String getMessage() {
        return MessageFormat.format(msg, field, data);
    }

}
