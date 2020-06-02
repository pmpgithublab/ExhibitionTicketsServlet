package ua.training.util;

import java.util.Locale;

import static ua.training.Constants.*;

public class LocaleUtil {
    private static final String ENG_QUERY_PART = "_en";
    private static final String UKR_QUERY_PART = "_uk";


    public static Locale getLocale() {
        return ResourceBundleManager.INSTANCE.getLocale();
    }

    public static void setLocale(Locale locale) {
        ResourceBundleManager.INSTANCE.setLocale(locale);
    }

    public static boolean isEnglish() {
        return !getLocale().equals(new Locale(UKRAINIAN_LOCALE_NAME));
    }

    public static String getProperty(String property) {
        return ResourceBundleManager.INSTANCE.getLocaleProperty(property);
    }

    public static String getLocalNameRegex() {
        if (isEnglish()) {
            return getProperty(REGEX_NAME_PATTERN);
        }
        return getProperty(REGEX_NAME_UK_PATTERN);
    }

    public static String localizeQuery(String query){
        if (LocaleUtil.isEnglish()){
            return query;
        }else {
            return query.replaceAll(ENG_QUERY_PART, UKR_QUERY_PART);
        }
    }
}
