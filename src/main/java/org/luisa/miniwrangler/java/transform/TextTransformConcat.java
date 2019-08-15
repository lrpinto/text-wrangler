package org.luisa.miniwrangler.java.transform;

import java.util.ArrayList;
import java.util.Arrays;
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

public class TextTransformConcat implements ITextTransformConcat {

    public static class Builder implements ITextTransformConcatBuilder {
        private String targetField;
        private final List<String> srcFields;
        private final List<Pattern> patterns;
        private String delimiter;

        public Builder() {
            srcFields = new ArrayList<>();
            patterns = new ArrayList<>();
            delimiter = "";
        }

        @Override
        public ITextTransformBuilder as(String targetField) {
            this.targetField = targetField;
            return this;
        }

        @Override
        public ITextTransform build() {
            return new TextTransformConcat(this);
        }

        @Override
        public ITextTransformBuilder concat(String... srcFields) {
            this.srcFields.addAll(Arrays.asList(srcFields));
            return this;
        }

        @Override
        public ITextTransformBuilder match(String pattern) {
            final Pattern mappedPattern = MappedPatternUtil.getInstance().get(pattern);
            patterns.add(mappedPattern);
            return this;
        }

        @Override
        public ITextTransformBuilder with(String delimiter) {
            this.delimiter = delimiter;
            return this;
        }
    }

    private final String targetField;
    private final List<String> srcFields;
    private final List<Pattern> patterns;
    private final String delimiter;

    public TextTransformConcat(Builder b) {
        targetField = b.targetField;
        srcFields = b.srcFields;
        patterns = b.patterns;
        delimiter = b.delimiter;
    }

    @Override
    public List<Pattern> getPatterns() {
        return patterns;
    }

    @Override
    public List<String> getSrcFields() {
        return srcFields;
    }

    @Override
    public String getTargetField() {
        return targetField;
    }

    @Override
    public void run(Map<String, Integer> srcFieldIndexMap, List<String> currentRow, Facade facade)
            throws UnknownIndexException, UnknownFieldException, PatternMismatchException, InvalidDataException {

        int i = 0;
        final StringBuilder sb = new StringBuilder();
        for (final String srcField : srcFields) {
            final Integer index = srcFieldIndexMap.get(srcField);

            if (index == null) {
                throw new UnknownIndexException(srcField);
            }

            final String value = currentRow.get(index);

            final Pattern pattern = patterns.get(i);
            final Matcher matcher = pattern.matcher(value);
            if (!matcher.matches()) {
                throw new PatternMismatchException(srcField, value, matcher);
            }

            sb.append(value).append(delimiter);

            i++;
        }

        sb.deleteCharAt(sb.length() - 1);

        facade.set(targetField, sb.toString());
    }

    @Override
    public String toString() {
        return "TextTransform [targetField=" + targetField + ", srcField=" + srcFields + ", patterns=" + patterns + "]";
    }

}
