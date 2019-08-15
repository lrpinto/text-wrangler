package org.luisa.miniwrangler.java.transform;

import java.util.List;
import java.util.Map;

import org.luisa.miniwrangler.java.exception.InvalidDataException;
import org.luisa.miniwrangler.java.exception.UnknownFieldException;
import org.luisa.miniwrangler.java.table.Facade;

/**
 * Represents a transformation rule 'fixedValue'
 *
 * @author Luisa Pinto
 *
 */
public class TextTransformFixedValue implements ITextTransformFixedValue {

    public static class Builder implements ITextTransformFixedValueBuilder {
        private String targetField;
        private String fixedValue;

        @Override
        public ITextTransformBuilder as(String targetField) {
            this.targetField = targetField;
            return this;
        }

        @Override
        public ITextTransform build() {
            return new TextTransformFixedValue(this);
        }

        @Override
        public ITextTransformBuilder fixedValue(String value) {
            fixedValue = value;
            return null;
        }

        @Override
        public ITextTransformBuilder match(String pattern) {
            throw new UnsupportedOperationException("Match not supported");
        }
    }

    private final String targetField;

    private final String fixedValue;

    public TextTransformFixedValue(Builder b) {
        targetField = b.targetField;
        fixedValue = b.fixedValue;
    }

    @Override
    public String getFixedValue() {
        return fixedValue;
    }

    @Override
    public String getTargetField() {
        return targetField;
    }

    @Override
    public void run(Map<String, Integer> srcFieldIndexMap, List<String> currentRow, Facade facade)
            throws UnknownFieldException, InvalidDataException {
        /*
         * TODO: This type of transformation ignores args srcFieldIndexMap and
         * currentRow, Break into different Run Interfaces Interfaces
         */
        facade.set(targetField, fixedValue);
    }

    @Override
    public String toString() {
        return "TextTransformFixedValue [targetField=" + targetField + ", fixedValue=" + fixedValue + "]";
    }

}
