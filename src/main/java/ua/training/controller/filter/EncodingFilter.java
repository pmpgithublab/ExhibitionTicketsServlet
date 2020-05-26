package ua.training.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

import static ua.training.Constants.*;

@WebFilter(EVERY_PATH)
public class EncodingFilter implements Filter {
    private static final String ENCODING = "UTF-8";
    private static final String CONTENT_TYPE = "text/html";

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        servletResponse.setCharacterEncoding(ENCODING);
        servletRequest.setCharacterEncoding(ENCODING);
        servletResponse.setContentType(CONTENT_TYPE);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
