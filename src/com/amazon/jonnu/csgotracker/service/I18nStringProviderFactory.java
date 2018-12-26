package com.amazon.jonnu.csgotracker.service;

import java.util.Locale;

public interface I18nStringProviderFactory {
    I18nStringProvider create(Locale locale);
}
