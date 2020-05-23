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
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        if (request.getSession().isNew()) {
            LocaleUtil.setLocale(new Locale(ENGLISH_LOCALE_NAME));
            Config.set(session, Config.FMT_LOCALE, LocaleUtil.getLocale());
            session.setAttribute(LANGUAGE, ENGLISH_LOCALE_NAME);
            session.setAttribute(CURRENCY_SIGN, DOLLAR_SIGN);
            DBQueryBundleManager.INSTANCE.setLocale(LocaleUtil.getLocale());
            DBPropertiesManager.INSTANCE.setLocale(LocaleUtil.getLocale());
        }

        if (request.getParameter(PARAM_LANG) != null) {
            Locale locale = new Locale(request.getParameter(PARAM_LANG));
            Config.set(request.getSession(), Config.FMT_LOCALE, locale);
            LocaleUtil.setLocale(locale);
            DBQueryBundleManager.INSTANCE.setLocale(LocaleUtil.getLocale());
            DBPropertiesManager.INSTANCE.setLocale(LocaleUtil.getLocale());
            session.setAttribute(LANGUAGE, locale);
            if (LocaleUtil.isEnglish()) {
                session.setAttribute(CURRENCY_SIGN, DOLLAR_SIGN);
            } else {
                session.setAttribute(CURRENCY_SIGN, HRYVNA_SIGN);
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
