package ua.training.model.util;

import java.util.Locale;
import java.util.ResourceBundle;

public enum DBQueryBundleManager {
    INSTANCE;

    private static final String PROPERTIES_FILE_NAME = "sql_query";

    private ResourceBundle resourceBundle = ResourceBundle.getBundle(PROPERTIES_FILE_NAME, Locale.getDefault());


    public void setLocale(Locale locale) {
        if (!resourceBundle.getLocale().equals(locale)) {
            resourceBundle = ResourceBundle.getBundle(PROPERTIES_FILE_NAME, locale);
        }
    }

    public String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
