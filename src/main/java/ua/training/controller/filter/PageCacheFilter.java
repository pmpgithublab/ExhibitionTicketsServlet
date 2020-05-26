package ua.training.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static ua.training.Constants.*;

@WebFilter(urlPatterns = EVERY_PATH)
public class PageCacheFilter implements Filter {
    private static final String PAGE_CACHE_CONTROL_HEADER_NAME = "Cache-Control";
    private static final String PAGE_CACHE_CONTROL_HEADER_VALUE = "no-cache, no-store, must-revalidate";

    private static final Set<String> notCachePath = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) {
        notCachePath.add(WELCOME_PATH);
        notCachePath.add(LOGIN_PATH);
        notCachePath.add(REGISTRATION_PATH);
        notCachePath.add(ERROR_PATH);
        notCachePath.add(LOGOUT_PATH);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        if (!notCachePath.contains(request.getServletPath())) {
            ((HttpServletResponse) servletResponse).setHeader(PAGE_CACHE_CONTROL_HEADER_NAME, PAGE_CACHE_CONTROL_HEADER_VALUE);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
