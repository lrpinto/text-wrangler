package org.luisa.miniwrangler.java.validation;

import javax.validation.Validation;

public class ValidatorInjectorImpl<T> implements ValidatorInjector<T> {

	@Override
	public ValidatorConsumer<T> getValidatorConsumer() {
		return new ValidatorConsumerImpl<>(
				Validation.buildDefaultValidatorFactory().getValidator());
	}

}
