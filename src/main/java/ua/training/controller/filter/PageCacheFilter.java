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

    private static final Set<String> notCachedPaths = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        notCachedPaths.add(WELCOME_PATH);
        notCachedPaths.add(LOGIN_PATH);
        notCachedPaths.add(REGISTRATION_PATH);
        notCachedPaths.add(ERROR_PATH);
        notCachedPaths.add(LOGOUT_PATH);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        if (!notCachedPaths.contains(request.getServletPath())) {
            ((HttpServletResponse) servletResponse).setHeader(PAGE_CACHE_CONTROL_HEADER_NAME, PAGE_CACHE_CONTROL_HEADER_VALUE);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
