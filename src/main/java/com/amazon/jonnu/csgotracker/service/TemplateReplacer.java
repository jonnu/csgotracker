package com.amazon.jonnu.csgotracker.service;

import java.util.Map;

import lombok.NonNull;

public interface TemplateReplacer {
    String replace(@NonNull String template, @NonNull Map<String, TemplateVariable> replacements);
}
