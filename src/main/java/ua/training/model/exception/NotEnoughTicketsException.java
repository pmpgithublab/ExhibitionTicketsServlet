package ua.training.model.exception;

public class NotEnoughTicketsException extends Exception {
    public NotEnoughTicketsException(String message) {
        super(message);
    }
}
