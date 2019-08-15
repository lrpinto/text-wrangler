package org.luisa.miniwrangler.java.validation;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public Validated<E, S> error(E error) {
        errors.add(error);
        return this;
    }

    @Override
    public List<E> errors() {
        return errors;
    }

    @Override
    public Validated<E, S> success(S success) {
        successes.add(success);
        return this;
    }

    @Override
    public List<S> successes() {
        return successes;
    }

}
