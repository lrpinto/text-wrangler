package org.luisa.miniwrangler.java.validation;

import java.util.List;

public interface Validated<E, S> {

    /**
     * Return textual representation of combined errors and successes
     *
     * @return the combined errors and success
     */
    String combined(Combinator<E, S> combinator);

    /**
     * Return this validated with the given error
     *
     * @return this validated with the error
     */
    Validated<E, S> error(E error);

    /**
     * Return a list of errors
     *
     * @return a list of errors
     */
    List<E> errors();

    /**
     * Return this validated with the given success
     *
     * @param success the given success
     * @return this validated with the success
     */
    Validated<E, S> success(S success);

    List<S> successes();
}
