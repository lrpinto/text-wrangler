package org.luisa.miniwrangler.java.transform;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Represent a IITextTransformConcat - a instance type of ITextTransformConcat
 * is created when a rule 'concat' is parsed
 *
 * @author Luisa Pinto
 *
 */
public interface ITextTransformConcat extends ITextTransform {

    /**
     * The patterns that the data values for the source fields of this
     * transformation should match
     *
     * @implNote it is assumed that a data value for the i-th source must match the
     *           i-th pattern
     *
     * @return a list of compiled patterns
     */
    List<Pattern> getPatterns();

    /**
     * The source fields for the data values that this transformation should
     * concatenate
     *
     * @implNote it is assumed that a data value for the i-th source must match the
     *           i-th pattern
     *
     * @return a list of source fields
     */
    List<String> getSrcFields();

}
