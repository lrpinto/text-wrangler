package org.luisa.miniwrangler.java.validation;

import java.util.Collection;
import java.util.Iterator;

public class JoinCombinator<E, S> implements Combinator<E, S> {

	@Override
	public String combineErrors(Collection<E> errors) {
		return join(errors);
	}

	@Override
	public String combineSuccesses(Collection<S> successes) {
		return join(successes);
	}

	private <O> String join(Collection<O> c) {
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
