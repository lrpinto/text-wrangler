package org.luisa.miniwrangler.java.transform;

/**
 * An interface that describes the contract for objects able to build builder
 * objects of type ITextTransformFixedValueBuilder
 *
 * @author Luisa Pinto
 *
 */
public interface ITextTransformFixedValueBuilder extends ITextTransformBuilder {

    /**
     * Build a ITextTransformConcatBuilder with the given source fixed value
     *
     * @param value the given value
     * @return this builder with the given value
     */
    ITextTransformBuilder fixedValue(String value);
}
