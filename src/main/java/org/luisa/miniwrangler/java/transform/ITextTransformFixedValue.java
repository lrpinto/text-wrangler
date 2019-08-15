package org.luisa.miniwrangler.java.transform;

/**
 * An interface that describes the contract for objects ITextTransformFixedValue
 * objects, which should represent a text transformation ITextTransform
 *
 * @implNote Each line in a transformations DSL specification with the rule
 *           'fixedValue', should be converted to a concrete implementation
 *           instance of this interface
 *
 * @author Luisa Pinto
 *
 */
public interface ITextTransformFixedValue extends ITextTransform {

    /**
     * Return the fixed value of this text transformation
     *
     * @return the fixed value
     */
    String getFixedValue();

}
