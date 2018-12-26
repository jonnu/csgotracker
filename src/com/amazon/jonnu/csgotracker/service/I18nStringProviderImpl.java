package com.amazon.jonnu.csgotracker.service;

import java.util.Locale;
import java.util.ResourceBundle;
import javax.annotation.Nullable;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import org.apache.commons.lang3.StringUtils;

public class I18nStringProviderImpl implements I18nStringProvider {

    private static final String RESOURCE_NAME = "i18n.Strings";

    private final Locale locale;

    @Inject
    public I18nStringProviderImpl(@Assisted final Locale locale) {
        this.locale = locale;
    }

    @Override
    @Nullable
    public String getString(final String key) {
        return getString(key, locale);
    }

    @Override
    @Nullable
    public String getString(final String key, final Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle(RESOURCE_NAME, locale);
        return bundle.getString(key);
    }

    @Override
    @Nullable
    public String getString(final String key, final Locale locale, final Object... parameters) {

        final String string = getString(key, locale);
        if (string == null) {
            return null;
        }

        if (StringUtils.countMatches(string, "{}") != parameters.length) {
            throw new IllegalArgumentException("Mismatch.");
        }

        return String.format(StringUtils.replace(string, "{}", "%s"), parameters);
    }

    public String buildNaturalList(final String[] elements) {

        final StringBuilder string = new StringBuilder();

        for (int i = 0; i < elements.length; i++) {

            String i18nKey;
            if (i == elements.length - 1) {
                i18nKey = "final";
            } else if (i == elements.length - 2) {
                i18nKey = "penultimate";
            } else {
                i18nKey = "each";
            }

            string.append(getString("general.list." + i18nKey, locale, elements[i]));
        }

        return string.toString();
    }
}
