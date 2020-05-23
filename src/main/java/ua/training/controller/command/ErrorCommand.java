package ua.training.controller.command;

import javax.servlet.http.HttpServletRequest;

import static ua.training.Constants.*;

public class ErrorCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return ERROR_PAGE;
    }
}
