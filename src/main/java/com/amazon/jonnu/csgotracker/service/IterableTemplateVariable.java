package com.amazon.jonnu.csgotracker.service;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class IterableTemplateVariable implements TemplateVariable<Iterable<String>> {

    private final String key;
    private final Iterable<String> variable;

    @Override
    public Class getType() {
        return Iterable.class;
    }

    @Override
    public boolean isEmpty() {
        return !variable.iterator().hasNext();
    }
}
