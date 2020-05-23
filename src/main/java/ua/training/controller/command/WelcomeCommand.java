package ua.training.controller.command;

import javax.servlet.http.HttpServletRequest;

import static ua.training.Constants.WELCOME_PAGE;

public class WelcomeCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return WELCOME_PAGE;
    }
}
