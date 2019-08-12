package org.luisa.miniwrangler.java.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

public class ValidatorConsumerImpl<T> implements ValidatorConsumer<T> {

	protected final Validator validator;

	public ValidatorConsumerImpl(Validator validator) {
		this.validator = validator;
	}

	@Override
	public Set<ConstraintViolation<T>> validate(T t) {
		return validator.validate(t);
	}
}