package ua.training.util;

import java.util.Locale;

import static ua.training.Constants.*;

public class LocaleUtil {
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
}
