package com.amazon.jonnu.csgotracker.service.i18n;

import java.util.Locale;
import java.util.Map;

import lombok.Builder;

@Builder
public class I18NRequest {
    private String string;
    private Locale locale;
    private Map<String, String> replacements;
}
