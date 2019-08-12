package org.luisa.miniwrangler.java.transform;

import java.util.regex.Pattern;

public interface ITextTransformRename extends ITextTransform {

	Pattern getPattern();

	String getSrcField();

	void setSrcFieldValue(String value);
}
