package com.amazon.jonnu.csgotracker.view;

import com.amazon.ask.model.Response;
import com.amazon.ask.response.ResponseBuilder;

import java.util.Collection;
import java.util.Optional;

public class RenderChain<T> {

    private final ResponseBuilder builder;
    private final Collection<Renderer<T>> renderers;

    public RenderChain(final Collection<Renderer<T>> renderers) {
        this.builder = new ResponseBuilder();
        this.renderers = renderers;
    }

    public RenderChain<T> apply(T toRender) {

        if (toRender == null) {
            return this;//Optional.empty();
        }

        renderers.forEach(contributor -> contributor.render(builder, toRender));

        return this;//builder.build();
    }

    public Optional<Response> getResponse() {
        return builder.build();
    }
}