package com.amazon.jonnu.csgotracker.injection.module;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;

import com.amazon.jonnu.csgotracker.service.IterableTemplateReplacer;
import com.amazon.jonnu.csgotracker.service.SimpleTemplateReplacer;
import com.amazon.jonnu.csgotracker.service.TemplateReplacer;
import com.amazon.jonnu.csgotracker.service.TemplateReplacerImpl;
import com.amazon.jonnu.csgotracker.service.TemplateVariableReplacer;

public class TemplateModule extends AbstractModule {

    protected void configure() {

        MapBinder<Class, TemplateVariableReplacer> typeReplacers = MapBinder.newMapBinder(binder(), Class.class, TemplateVariableReplacer.class);
        typeReplacers.addBinding(String.class).to(SimpleTemplateReplacer.class);
        typeReplacers.addBinding(new TypeLiteral<Iterable<String>>() {}.getClass()).to(IterableTemplateReplacer.class);

        bind(TemplateReplacer.class).to(TemplateReplacerImpl.class);

        //TemplateVariableReplacer
    }
}
