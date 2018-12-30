package com.amazon.jonnu.csgotracker.service;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TemplateReplacement {
    private final int start;
    private final int end;
    private final String replacement;
}
