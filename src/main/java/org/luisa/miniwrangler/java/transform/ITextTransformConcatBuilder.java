package org.luisa.miniwrangler.java.transform;

/**
 * An interface that describes the contract for objects able to build builder
 * objects of type ITextTransformConcatBuilder
 *
 * @author Luisa Pinto
 *
 */
public interface ITextTransformConcatBuilder extends ITextTransformBuilder {

    /**
     * Build a ITextTransformConcatBuilder with the given source fields
     *
     * @implNote the values of the sources fields should be later concatenated
     *
     * @param srcFields the given source fields
     * @return
     */
    ITextTransformBuilder concat(String... srcFields);

    /**
     * Build a ITextTransformConcatBuilder with the given token
     *
     * @implNote the token should be used as delimiter for the concatenated values
     *           resulting from this transformation
     *
     * @param token the given token
     * @return this builder with the given token
     */
    ITextTransformBuilder with(String token);

}
