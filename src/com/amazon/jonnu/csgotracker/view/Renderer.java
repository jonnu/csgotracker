package com.amazon.jonnu.csgotracker.view;

import com.amazon.ask.model.Response;
import com.amazon.ask.response.ResponseBuilder;
import lombok.NonNull;

import java.util.Optional;

@FunctionalInterface
public interface Renderer<T> {

    default Optional<Response> render(@NonNull final T data) {
        return render(data, new ResponseBuilder());
    }

    Optional<Response> render(@NonNull final T data, @NonNull final ResponseBuilder builder);
}
