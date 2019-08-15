package org.luisa.miniwrangler.java.validation;

import java.util.List;

public interface Validated<E, S> {

	String combined(Combinator<E, S> combinator);

	Validated<E, S> error(E error);

	List<E> errors();

	Validated<E, S> success(S success);

	List<S> successes();
}
