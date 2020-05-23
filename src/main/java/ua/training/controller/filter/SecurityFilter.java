package ua.training.controller.filter;

import org.apache.log4j.Logger;
import ua.training.controller.command.LogOutCommand;
import ua.training.model.entity.UserRole;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static ua.training.Constants.*;

@WebFilter(EVERY_PATH)
public class SecurityFilter implements Filter {
    private static final Logger log = Logger.getLogger(SecurityFilter.class);
    private static final String UNAUTHORIZED_ACCESS_OR_WRONG_WAY_REQUEST = "Unauthorized access or wrong way request. Path: ";
    private static final String USER_FORCED_LOGGED_OUT = "User is forced logged out. Email: ";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final String path = request.getServletPath();
        final HttpSession session = request.getSession();
        final UserRole currentUserRole = (UserRole) session.getAttribute(USER_ROLE);


        // todo refactor to map
        if (path.startsWith(TRADE_PATH) && UserRole.ROLE_USER.equals(currentUserRole)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (path.startsWith(ADMIN_PATH) && UserRole.ROLE_ADMIN.equals(currentUserRole)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (path.equals(WELCOME_PATH) || path.equals(LOGIN_PATH) || path.equals(REGISTRATION_PATH)) {
            if (UserRole.ROLE_ANONYMOUS.equals(currentUserRole)) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                ((HttpServletResponse) servletResponse).sendRedirect(new LogOutCommand().execute(request));
            }
            return;
        }

        if (path.equals(ERROR_PATH) || path.equals(LOGOUT_PATH)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        log.warn(UNAUTHORIZED_ACCESS_OR_WRONG_WAY_REQUEST + path + ROLE + currentUserRole + SESSION_ID + session.getId());
        if (!UserRole.ROLE_ANONYMOUS.equals(currentUserRole)) {
            log.info(USER_FORCED_LOGGED_OUT + session.getAttribute(USER_EMAIL));
            new LogOutCommand().execute(request);
        }
        ((HttpServletResponse) servletResponse).sendRedirect(request.getContextPath() + ERROR_PATH);
    }

    @Override
    public void destroy() {

    }
}