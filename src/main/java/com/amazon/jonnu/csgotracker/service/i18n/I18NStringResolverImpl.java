package com.amazon.jonnu.csgotracker.service.i18n;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import com.amazon.jonnu.csgotracker.service.SimpleTemplateVariable;
import com.amazon.jonnu.csgotracker.service.TemplateReplacer;

@Slf4j
@Singleton
public class I18NStringResolverImpl implements I18NStringResolver {

    private static final String RESOURCE_NAME = "i18n.Strings";
    private static final Locale DEFAULT_LOCALE = Locale.US;

    private static Map<Locale, ResourceBundle> CACHE = new HashMap<>();

    private final TemplateReplacer replacer;

    @Getter @Setter @Accessors(chain = true)
    private Locale locale;

    @Inject
    public I18NStringResolverImpl(@NonNull final TemplateReplacer replacer) {
        this.locale = DEFAULT_LOCALE;
        this.replacer = replacer;
    }

    public String resolve(final String string) {
        return resolve(string, Collections.emptyMap());
    }

    public String resolve(final String string, final Map<String, String> replacements) {

        final ResourceBundle bundle = getStashedBundle(locale);

        if (!bundle.containsKey(string)) {
            throw new I18NException("No such string '" + string + "' (" + locale + ")");
        }

        String template = bundle.getString(string);

        log.info("Before: {}", template);

        // Perform replacements
        String replaced = replacer.replace(template, replacements.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> SimpleTemplateVariable.builder().key(entry.getKey()).variable(entry.getValue()).build())));

        log.info("After: {}", replaced);

        // Return string.
        return replaced;
    }

    private ResourceBundle getStashedBundle(final Locale locale) {
        return CACHE.computeIfAbsent(locale, loc -> ResourceBundle.getBundle(RESOURCE_NAME, loc, ResourceBundle.Control.getNoFallbackControl(ResourceBundle.Control.FORMAT_PROPERTIES)));
    }

}
