package com.amazon.jonnu.csgotracker.service;

import java.util.Locale;

public interface I18nStringProvider {
    String getString(String key);
    String getString(String key, Locale locale);
    String getString(String key, Locale locale, Object... parameters);
}
