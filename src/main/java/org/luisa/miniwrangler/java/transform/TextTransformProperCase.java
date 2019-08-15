package org.luisa.miniwrangler.java.transform;

import java.util.List;
import java.util.Map;

import org.luisa.miniwrangler.java.data.utils.StringUtils;
import org.luisa.miniwrangler.java.exception.InvalidDataException;
import org.luisa.miniwrangler.java.exception.PatternMismatchException;
import org.luisa.miniwrangler.java.exception.UnknownFieldException;
import org.luisa.miniwrangler.java.exception.UnknownIndexException;
import org.luisa.miniwrangler.java.table.Facade;

/**
 * Represents a transformation rule 'properCase'
 *
 * @author Luisa Pinto
 *
 */
public class TextTransformProperCase extends TextTransformRename {

    public TextTransformProperCase(Builder b) {
        super(b);
    }

    @Override
    public void run(Map<String, Integer> srcFieldIndexMap, List<String> currentRow, Facade facade)
            throws UnknownFieldException, UnknownIndexException, PatternMismatchException, InvalidDataException {

        super.run(srcFieldIndexMap, currentRow, facade);
        final String targetField = getTargetField();
        final String value = (String) facade.get(targetField);
        final String properCased = StringUtils.toProperCase(value);
        facade.set(targetField, properCased);
    }

}
