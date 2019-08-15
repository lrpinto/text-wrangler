package org.luisa.miniwrangler.java.validation;

import java.util.Collection;

import org.luisa.miniwrangler.java.validation.utils.CollectionsUtils;

/**
 * Combinator or errors and successes
 *
 * @author Liusa Pinto
 *
 * @param <E> this type should represent an error
 * @param <S> this type should represent a success
 */
public class JoinCombinator<E, S> implements Combinator<E, S> {

    /*
     * (non-javadoc)
     *
     * @see
     * org.luisa.miniwrangler.java.validation.Combinator.combineErrors(Collection<E>
     * )
     */
    @Override
    public String combineErrors(Collection<E> errors) {
        return CollectionsUtils.join(errors);
    }

    /*
     * (non-javadoc)
     *
     * @see
     * org.luisa.miniwrangler.java.validation.Combinator.combineSuccesses(Collection
     * <E>)
     */
    @Override
    public String combineSuccesses(Collection<S> successes) {
        return CollectionsUtils.join(successes);
    }

}
