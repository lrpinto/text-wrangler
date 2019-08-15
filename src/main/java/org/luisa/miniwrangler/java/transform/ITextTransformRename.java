package org.luisa.miniwrangler.java.transform;

import java.util.regex.Pattern;

/**
 * An interface that describes the contract for objects ITextTransformRename
 * objects, which should represent a text transformation ITextTransform
 *
 * @implNote Each line in a transformations DSL specification with the rule
 *           'rename', should be converted to a concrete implementation instance
 *           of this interface
 *
 * @author Luisa Pinto
 *
 */
public interface ITextTransformRename extends ITextTransform {

    /**
     * The pattern that the data values for the source fields of this transformation
     * should match
     *
     * @return a compiled pattern
     */
    Pattern getPattern();

    /**
     * The source field for the data values this transformation works on
     *
     *
     * @return a source field
     */
    String getSrcField();

}
