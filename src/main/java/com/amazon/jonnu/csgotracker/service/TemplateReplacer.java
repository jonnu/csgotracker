package com.amazon.jonnu.csgotracker.service;

@FunctionalInterface
public interface TemplateReplacer<T> {
    String replace(TemplateVariable<T> data);
}
