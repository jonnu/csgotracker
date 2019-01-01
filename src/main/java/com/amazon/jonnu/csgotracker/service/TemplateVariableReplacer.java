package com.amazon.jonnu.csgotracker.service;

@FunctionalInterface
public interface TemplateVariableReplacer<T> {
    String replace(TemplateVariable<T> data);
}
