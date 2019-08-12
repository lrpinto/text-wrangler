package org.luisa.miniwrangler.java.validation;

public interface ValidatorInjector<T> {

	ValidatorConsumer<T> getValidatorConsumer();
}
