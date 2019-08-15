/**
 * CollectionsUtils.java
 *
 * @author Luisa Pinto
 */
package org.luisa.miniwrangler.java.validation.utils;

import java.util.Collection;
import java.util.Iterator;

/**
 * A utility class to manipulate and perform operations over Collection objects
 *
 * @author Luisa Pinto
 *
 */
public class CollectionsUtils {

    /**
     * Return a string representation of the items of this collection seprated by a
     * comma
     *
     * @param <O> the type of each element in the given collection
     * @param c   the collection
     * @return a textual representation of the joined collection
     */
    public static <O> String join(Collection<O> c) {
        if (c == null || c.isEmpty()) {
            return "";
        }

        final Iterator<O> it = c.iterator();
        final StringBuilder sb = new StringBuilder();

        if (it.hasNext()) {
            sb.append(it.next());
        }

        while (it.hasNext()) {
            sb.append(", ")
                    .append(it.next());
        }

        return sb.toString();
    }

}
