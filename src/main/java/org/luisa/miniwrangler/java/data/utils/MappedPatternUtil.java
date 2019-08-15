package org.luisa.miniwrangler.java.data.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * A utility class that maps commonly used patterns to a Java Regex Pattern <br>
 * - Mapped patterns can be used as parameter for rule 'match' in a DSL
 * transformation <br>
 * - This is singleton instance that when is initialised precompiles all that
 * patterns that can be used <br>
 * - It also provides a util to check whether a given String matches a target
 * pattern
 *
 * @author Luisa Pinto
 */
public class MappedPatternUtil {

    private static MappedPatternUtil mpu;

    /**
     * Create or return the singleton MappedpatternUtil instance
     *
     * @return a singleton MappedPatternUtil
     */
    public static MappedPatternUtil getInstance() {
        if (mpu == null) {
            mpu = new MappedPatternUtil();
        }
        return mpu;
    }

    private Map<String, Pattern> mappedPatterns;

    /*
     * Private constructor to be used internally
     */
    private MappedPatternUtil() {
        initPatternMap();
    }

    /**
     * Return a target pattern
     *
     * @param pattern the requested pattern
     * @return the pattern
     */
    public Pattern get(String pattern) {
        return mappedPatterns.get(pattern);
    }

    /*
     * Auxiliary method that initialises and precompiles the mapped pattern
     */
    private void initPatternMap() {
        mappedPatterns = new HashMap<>();
        mappedPatterns.put("YYYY", Pattern.compile("^\\d{4}$"));
        mappedPatterns.put("MM", Pattern.compile("^0*([1-9]|1[0-2])$"));
        mappedPatterns.put("dd", Pattern.compile("^0*([1-9]|[12][0-9]|3[01])$"));
        mappedPatterns.put("d+", Pattern.compile("\\d+"));
        mappedPatterns.put("#,##0.0#", Pattern.compile("([1-9],)?[0-9]{0,2}[0-9]\\.[0-9]([0-9])?"));
    }

}
