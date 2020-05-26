package ua.training.controller.command;

import javax.servlet.http.HttpServletRequest;

public class WelcomeCommand implements Command {
    private static final String WELCOME_PAGE = "/WEB-INF/index.jsp";


    @Override
    public String execute(HttpServletRequest request) {
        return WELCOME_PAGE;
    }
}
