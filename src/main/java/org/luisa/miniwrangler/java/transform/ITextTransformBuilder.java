package org.luisa.miniwrangler.java.transform;

/**
 * Builder interface defines different ways to configure a text transformation.
 */
public interface ITextTransformBuilder {

	ITextTransformBuilder as(String orderField);

	ITextTransform build();

	ITextTransformBuilder match(String pattern);

}
