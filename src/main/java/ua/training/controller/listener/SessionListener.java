package ua.training.controller.listener;

import org.apache.log4j.Logger;
import ua.training.model.dto.TicketDTO;
import ua.training.model.entity.UserRole;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;

import static ua.training.Constants.*;

@WebListener
public class SessionListener implements HttpSessionListener {
    private static final Logger log = Logger.getLogger(SessionListener.class);
    private static final String SESSION_CREATED = "Session created. Id: ";
    private static final String SESSION_DESTROYED = "Session destroyed. Id: ";

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        httpSessionEvent.getSession().setAttribute(USER_ROLE, UserRole.ROLE_ANONYMOUS);
        log.info(SESSION_CREATED + httpSessionEvent.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        log.info(SESSION_DESTROYED + httpSessionEvent.getSession().getId());
    }
}
