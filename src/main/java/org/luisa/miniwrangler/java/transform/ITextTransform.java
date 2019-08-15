package org.luisa.miniwrangler.java.transform;

import java.util.List;
import java.util.Map;

import org.luisa.miniwrangler.java.exception.InvalidDataException;
import org.luisa.miniwrangler.java.exception.PatternMismatchException;
import org.luisa.miniwrangler.java.exception.UnknownFieldException;
import org.luisa.miniwrangler.java.exception.UnknownIndexException;
import org.luisa.miniwrangler.java.table.Facade;

/**
 * Represents a text transformation - Each line in a transformations DSL
 * specification should be converted to an instance of a subclass of this
 * interface
 *
 * @author Luisa Pinto
 *
 */
public interface ITextTransform {

    /**
     * Return the target field for this transformation
     *
     * @return the target field
     */
    String getTargetField();

    /**
     * Run this transformation, which consists of applying given data to given the
     * target object given a source field index map
     *
     * - if src field 'A' has index 'i', then it assumes the value will be at
     * data[i]
     *
     * @param srcFieldIndexMap source field index map
     * @param data             the data
     * @param targetObject     the target object
     * @throws UnknownIndexException    if the source for this transformation does
     *                                  not exist in the srcFieldIndexMap
     * @throws UnknownFieldException    if the target field for this transformation
     *                                  does not exist in the target object
     * @throws PatternMismatchException if the pattern specified for the the
     *                                  corresponding data value is not matched
     * @throws InvalidDataException     if the data cannot be assigned to the target
     *                                  field of the target object
     */
    void run(Map<String, Integer> srcFieldIndexMap, List<String> data, Facade targetObject)
            throws UnknownIndexException, UnknownFieldException, PatternMismatchException, InvalidDataException;

}
