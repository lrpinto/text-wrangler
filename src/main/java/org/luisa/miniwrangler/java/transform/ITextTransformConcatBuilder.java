package org.luisa.miniwrangler.java.transform;

public interface ITextTransformConcatBuilder extends ITextTransformBuilder {

	ITextTransformBuilder concat(String... srcField);

}
