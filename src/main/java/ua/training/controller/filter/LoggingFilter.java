package ua.training.controller.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static ua.training.Constants.*;

@WebFilter(urlPatterns = EVERY_PATH)
public class LoggingFilter implements Filter {
    private static final Logger log = Logger.getLogger(LoggingFilter.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();

        StringBuilder sb = new StringBuilder();
        sb.append(NEW_PATH).append(request.getRequestURL());
        if (request.getQueryString() != null) {
            sb.append(QUERY_SYMBOL).append(request.getQueryString());
        }
        sb.append(METHOD).append(request.getMethod());
        sb.append(ROLE).append(session.getAttribute(USER_ROLE));
        sb.append(EMAIL).append(session.getAttribute(USER_EMAIL));
        sb.append(SESSION_ID).append(session.getId());
        log.info(sb.toString());

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
