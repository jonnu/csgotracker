package com.amazon.jonnu.csgotracker.service;

public interface TemplateVariable<T> {
    Class getType();
    String getKey();
    T getVariable();
    boolean isEmpty();
}
