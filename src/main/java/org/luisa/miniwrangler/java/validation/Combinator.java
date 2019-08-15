package org.luisa.miniwrangler.java.validation;

import java.util.Collection;

public interface Combinator<E, S> {

	String combineErrors(Collection<E> errors);

	String combineSuccesses(Collection<S> successes);

}
