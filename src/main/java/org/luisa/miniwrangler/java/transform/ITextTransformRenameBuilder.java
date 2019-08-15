package org.luisa.miniwrangler.java.transform;

/**
 * An interface that describes the contract for objects able to build builder
 * objects of type ITextTransformRenameBuilder
 *
 * @author Luisa Pinto
 *
 */
public interface ITextTransformRenameBuilder extends ITextTransformBuilder {

    /**
     * Build a ITextTransformConcatBuilder with the given source field
     *
     * @param srcField the given source field
     * @return this builder with the given source field
     */
    ITextTransformBuilder rename(String srcField);

}
