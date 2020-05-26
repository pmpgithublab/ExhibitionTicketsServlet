package ua.training.controller.filter;

import ua.training.util.LocaleUtil;
import ua.training.model.util.DBPropertiesManager;
import ua.training.model.util.DBQueryBundleManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.util.Locale;

import static ua.training.Constants.*;

@WebFilter(EVERY_PATH)
public class LocaleFilter implements Filter {
    private static final String PARAM_LANG = "lang";

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();

        if (request.getSession().isNew()) {
            setLocale(ENGLISH_LOCALE_NAME, session);
        }

        if (request.getParameter(PARAM_LANG) != null) {
            setLocale(request.getParameter(PARAM_LANG), session);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void setLocale(String localeString, HttpSession session) {
        Locale locale = new Locale(localeString);
        LocaleUtil.setLocale(locale);
        Config.set(session, Config.FMT_LOCALE, LocaleUtil.getLocale());
        DBQueryBundleManager.INSTANCE.setLocale(LocaleUtil.getLocale());
        DBPropertiesManager.INSTANCE.setLocale(LocaleUtil.getLocale());
        session.setAttribute(LANGUAGE, locale);
        if (LocaleUtil.isEnglish()) {
            session.setAttribute(CURRENCY_SIGN, DOLLAR_SIGN);
        } else {
            session.setAttribute(CURRENCY_SIGN, HRYVNA_SIGN);
        }
    }

    @Override
    public void destroy() {

    }
}
