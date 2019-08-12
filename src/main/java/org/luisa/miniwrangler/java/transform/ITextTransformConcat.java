package org.luisa.miniwrangler.java.transform;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public interface ITextTransformConcat extends ITextTransform {

	void addSrcFieldValueMap(Map<String, String> valueMap);

	List<Pattern> getPatterns();

	List<String> getSrcFields();

}
