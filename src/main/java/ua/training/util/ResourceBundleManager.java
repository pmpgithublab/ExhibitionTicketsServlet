/*
 * @(#)ResourceManager.java     1.00 2020/03/11
 *
 * This software is free for use.
 */

package ua.training.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Localization manager class
 *
 * @author Firstname Lastname
 * @version 1.00 11 Mar 2020
 */
public enum ResourceBundleManager {
    INSTANCE;

    private static final String PROPERTIES_FILE_NAME = "messages";

    private ResourceBundle resourceBundle = ResourceBundle.getBundle(PROPERTIES_FILE_NAME, Locale.getDefault());


    public void setLocale(Locale locale) {
        if (!resourceBundle.getLocale().equals(locale)) {
            resourceBundle = ResourceBundle.getBundle(PROPERTIES_FILE_NAME, locale);
        }
    }

    public Locale getLocale(){
        return resourceBundle.getLocale();
    }

    public String getLocaleProperty(String key) {
        return resourceBundle.getString(key);
    }
}
