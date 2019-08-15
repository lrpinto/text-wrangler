package org.luisa.miniwrangler.java.validation;

import java.util.Collection;

/**
 * Combinator or errors or successes
 *
 * @author developer
 *
 * @param <E> this type should represent an error
 * @param <S> this type should represent a success
 */
public interface Combinator<E, S> {

    /**
     * Combines a collection of errors
     *
     * @param errors the given collection
     * @return a String a textual representation of combined errors
     */
    String combineErrors(Collection<E> errors);

    /**
     * Combines a collection of successes
     *
     * @param successes the given collection
     * @return a String a textual representation of combined successes
     */
    String combineSuccesses(Collection<S> successes);

}
