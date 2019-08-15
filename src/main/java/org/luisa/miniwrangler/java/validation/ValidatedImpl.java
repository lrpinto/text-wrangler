package org.luisa.miniwrangler.java.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implemantation of an instance of Validate<E,S>
 *
 * Objects of this class allow a client to build successes and errors about an
 * object's validaty
 *
 * @author Luisa Pinto
 *
 * @param <E> this type should represent an error
 * @param <S> this type should represent a success
 */
public class ValidatedImpl<E, S> implements Validated<E, S> {

    protected List<E> errors;
    protected List<S> successes;

    /**
     * Constructs an instance of a IValidated type
     *
     * @return a new ValidatedImpl
     */
    public ValidatedImpl() {
        errors = new ArrayList<>();
        successes = new ArrayList<>();
    }

    /*
     * (non-javadoc)
     *
     * @see org.luisa.miniwrangler.java.validation.ValidatedImpl#
     * .combined(Combinator<E,S> combinator)
     */
    @Override
    public String combined(Combinator<E, S> combinator) {
        final StringBuilder sb = new StringBuilder();
        sb.append("Successes: [")
                .append(combinator.combineSuccesses(successes))
                .append("]\n")
                .append("Errors: [")
                .append(combinator.combineErrors(errors))
                .append("]");
        return sb.toString();
    }

    /*
     * (non-javadoc)
     *
     * @see org.luisa.miniwrangler.java.validation.ValidatedImpl# .error(E error)
     */
    @Override
    public Validated<E, S> error(E error) {
        errors.add(error);
        return this;
    }

    /*
     * (non-javadoc)
     *
     * @see org.luisa.miniwrangler.java.validation.ValidatedImpl# .errors()
     */
    @Override
    public List<E> errors() {
        return errors;
    }

    /*
     * (non-javadoc)
     *
     * @see org.luisa.miniwrangler.java.validation.ValidatedImpl# .success(S
     * success)
     */
    @Override
    public Validated<E, S> success(S success) {
        successes.add(success);
        return this;
    }

    /*
     * (non-javadoc)
     *
     * @see org.luisa.miniwrangler.java.validation.ValidatedImpl# .successes()
     */
    @Override
    public List<S> successes() {
        return successes;
    }

}
