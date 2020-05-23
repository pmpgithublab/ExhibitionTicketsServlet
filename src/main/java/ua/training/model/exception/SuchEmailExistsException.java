package ua.training.model.exception;

public class SuchEmailExistsException extends Exception {
    private final String login;


    public SuchEmailExistsException(String message, String login) {
        super(message);
        this.login = login;
    }

    public String getLogin() {
        return login;
    }
}
