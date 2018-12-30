package com.amazon.jonnu.csgotracker.service;

import lombok.Builder;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;

@Value
@Builder
public class SimpleTemplateVariable implements TemplateVariable<String> {

    public static final SimpleTemplateVariable EMPTY = SimpleTemplateVariable.builder()
            .variable(StringUtils.EMPTY)
            .build();

    private final String key;
    private final String variable;

    @Override
    public Class getType() {
        return String.class;
    }

    @Override
    public boolean isEmpty() {
        return StringUtils.isBlank(variable);
    }
}
