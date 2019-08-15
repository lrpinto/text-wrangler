package org.luisa.miniwrangler.java.table;

import org.luisa.miniwrangler.java.exception.InvalidDataException;
import org.luisa.miniwrangler.java.exception.UnknownFieldException;

/**
 * A class to allow transformations to apply them selves to concrete objects
 *
 * @author Luisa Pinto
 *
 */
public interface Facade {

    /**
     * Return the value for the given source field
     *
     * @param field the given source field
     * @return the value of the source field
     * @throws UnknownFieldException if the field does not exist
     */
    Object get(String field) throws UnknownFieldException;

    /**
     * Set the given String as value for the given source field
     * 
     * @param field the given source field
     * @param value the given value represented as String
     * @throws UnknownFieldException if the field does not exist
     * @throws InvalidDataException  if the String value cannot be converted to a
     *                               valid value type
     */
    void set(String field, String value) throws UnknownFieldException, InvalidDataException;

}
