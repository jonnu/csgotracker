package com.amazon.jonnu.csgotracker.service;

public class SimpleTemplateReplacer implements TemplateReplacer<String> {

    @Override
    public String replace(final TemplateVariable<String> data) {
        return data.getVariable();
    }

}
