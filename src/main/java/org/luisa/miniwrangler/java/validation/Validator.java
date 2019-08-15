package org.luisa.miniwrangler.java.validation;

/**
 * Validator interface
 *
 * @author Luisa Pinto
 *
 * @param <E> This type should represent a validation error
 * @param <S> This type should represent a validation success
 */
public interface Validator<E, S> {

    /**
     * Validates the caller object and returns a Validated object containing the
     * result of the validation
     *
     * @return an object containing the result of validated object
     */
    Validated<E, S> validate();
}
