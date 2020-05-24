package ua.training.controller.command;

import javax.servlet.http.HttpServletRequest;

public class ErrorCommand implements Command {
    private static final String ERROR_PAGE = "/WEB-INF/error.jsp";

    @Override
    public String execute(HttpServletRequest request) {
        return ERROR_PAGE;
    }
}
