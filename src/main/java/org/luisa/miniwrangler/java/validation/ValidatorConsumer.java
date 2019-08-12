package org.luisa.miniwrangler.java.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;

public interface ValidatorConsumer<T> {
	Set<ConstraintViolation<T>> validate(T t);
}