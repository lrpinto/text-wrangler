package org.luisa.miniwrangler.java.transform;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.luisa.miniwrangler.java.data.utils.MappedPatternUtil;
import org.luisa.miniwrangler.java.exception.InvalidDataException;
import org.luisa.miniwrangler.java.exception.PatternMismatchException;
import org.luisa.miniwrangler.java.exception.UnknownFieldException;
import org.luisa.miniwrangler.java.exception.UnknownIndexException;
import org.luisa.miniwrangler.java.table.Facade;

public class TextTransformRename implements ITextTransformRename {

    public static class Builder implements ITextTransformRenameBuilder {
        private String targetField;
        private String srcField;
        private Pattern pattern;

        @Override
        public ITextTransformBuilder as(String targetField) {
            this.targetField = targetField;
            return this;
        }

        @Override
        public ITextTransform build() {
            return new TextTransformRename(this);
        }

        @Override
        public ITextTransformBuilder match(String regex) {
            Pattern pattern = MappedPatternUtil.getInstance().get(regex);
            if (pattern == null) {
                pattern = Pattern.compile(regex);
            }
            this.pattern = pattern;
            return this;
        }

        @Override
        public ITextTransformBuilder rename(String srcField) {
            this.srcField = srcField;
            return this;
        }
    }

    private final String targetField;
    private final String srcField;
    private final Pattern pattern;

    private String srcFieldValue;

    public TextTransformRename(Builder b) {
        targetField = b.targetField;
        srcField = b.srcField;
        pattern = b.pattern;
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }

    @Override
    public String getSrcField() {
        return srcField;
    }

    @Override
    public String getTargetField() {
        return targetField;
    }

    @Override
    public void run(Map<String, Integer> srcFieldIndexMap, List<String> currentRow, Facade facade)
            throws UnknownFieldException, UnknownIndexException, PatternMismatchException, InvalidDataException {

        final Integer index = srcFieldIndexMap.get(srcField);
        if (index == null) {
            throw new UnknownIndexException(srcField);
        }

        final String value = currentRow.get(index);
        setSrcFieldValue(value);

        if (pattern != null) {
            final Matcher matcher = pattern.matcher(srcFieldValue);
            if (!matcher.matches()) {
                throw new PatternMismatchException(srcField, value, matcher);
            }
        }

        facade.set(targetField, srcFieldValue);
    }

    @Override
    public void setSrcFieldValue(String value) {
        srcFieldValue = value;
    }

    @Override
    public String toString() {
        return "TextTransform [targetField=" + targetField + ", srcField=" + srcField + ", pattern=" + pattern + "]";
    }

}
