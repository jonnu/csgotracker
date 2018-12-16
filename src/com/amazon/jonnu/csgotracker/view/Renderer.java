package com.amazon.jonnu.csgotracker.view;

import com.amazon.ask.response.ResponseBuilder;
import lombok.NonNull;

@FunctionalInterface
public interface Renderer<T> {
    void render(@NonNull final ResponseBuilder builder, @NonNull final T data);
}
