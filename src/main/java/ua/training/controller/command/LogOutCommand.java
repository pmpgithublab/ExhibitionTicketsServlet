package ua.training.controller.command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static ua.training.Constants.*;

public class LogOutCommand implements Command {
    private static final Logger log = Logger.getLogger(LogOutCommand.class);
    private static final String USER_LOGGED_OUT = "User is logged out. Email: ";

    @Override
    public String execute(HttpServletRequest request) {
        log.info(USER_LOGGED_OUT + request.getAttribute(USER_EMAIL) + SESSION_ID + request.getSession().getId());
        request.getSession().invalidate();

        return REDIRECT_STRING + WELCOME_PATH;
    }
}
