package ua.training.model.dto.validator;

public interface Validator<T> {
    boolean isValid(T request);
}
