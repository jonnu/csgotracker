package com.amazon.jonnu.csgotracker.service.i18n;

import java.util.Locale;
import java.util.Map;

public interface I18NStringResolver {
    I18NStringResolver setLocale(Locale locale);
    Locale getLocale();
    String resolve(String string);
    String resolve(String string, Map<String, String> replacements);
}
