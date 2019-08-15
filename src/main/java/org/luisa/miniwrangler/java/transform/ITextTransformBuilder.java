package org.luisa.miniwrangler.java.transform;

/**
 * An interface that describes the contract for objects able to build builder
 * objects of type ITextTransformBuilder
 *
 * @author Luisa Pinto
 */
public interface ITextTransformBuilder {

    /**
     * Specify the targetField for this transformation
     *
     * @param targetField the target field
     * @return this object with a target field specified
     */
    ITextTransformBuilder as(String targetField);

    /**
     * Build some instance of type of ITextTransform
     *
     * @return the instance of ITextTransform type
     */
    ITextTransform build();

    /**
     * Specify the data pattern to which this transformation should be applied
     *
     * @param pattern the pattern
     * @return this object with a pattern specified
     */
    ITextTransformBuilder match(String pattern);

}
