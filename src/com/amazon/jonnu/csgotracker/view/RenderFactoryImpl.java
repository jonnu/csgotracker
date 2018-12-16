package com.amazon.jonnu.csgotracker.view;

import com.amazon.ask.model.Response;
import com.amazon.ask.response.ResponseBuilder;
import com.amazon.jonnu.csgotracker.model.TeamRoster;
import com.amazon.jonnu.csgotracker.view.roster.RosterCardRenderer;
import com.amazon.jonnu.csgotracker.view.roster.RosterSpeechRenderer;
import com.google.common.collect.SetMultimap;
import com.google.common.primitives.Primitives;
import com.google.common.reflect.TypeToInstanceMap;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

public class RenderFactoryImpl implements RenderFactory {

    private final SetMultimap<Class<?>, Renderer> renderers;

    RenderFactoryImpl(final SetMultimap<Class<?>, Renderer> renderers) {
        this.renderers = renderers;

        // Temp
        renderers.put(TeamRoster.class, new RosterCardRenderer());
        renderers.put(TeamRoster.class, new RosterSpeechRenderer());

        Map<Class<?>, >
    }

    public <T> RenderChain<T> getRenderChain(final Class<T> typeToRender) {

        Set<Renderer<T>> setty = (Set<Renderer<T>>) renderers.get(typeToRender);
        return new RenderChain<>(setty);

        TypeToInstanceMap

        /*Set<Renderer<T>> renderSet = renderers.get(typeToRender);
        renderSet.stream().map(r -> typeToRender)
        return new RenderChain<T>((Collection<Renderer<?>>) renderSet);*/
    }

    private static Supplier<ResponseBuilder> getResponseBuilder() {
        return ResponseBuilder::new;
    }
    //ResponseBuilder builder = new ResponseBuilder();

    class TypedRendererKey<T> {
        private String
    }
}
