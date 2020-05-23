package ua.training.model.exception;

public class NoDuplicationAllowedException extends Exception {
    public NoDuplicationAllowedException(Exception exception) {
        super(exception);
    }
}
