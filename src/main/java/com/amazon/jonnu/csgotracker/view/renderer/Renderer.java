package com.amazon.jonnu.csgotracker.view.renderer;

import java.util.Optional;

import lombok.NonNull;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import com.amazon.ask.response.ResponseBuilder;

public interface Renderer<T> {

    default Optional<Response> render(@NonNull HandlerInput input, @NonNull final T data) {
        return render(input, data, new ResponseBuilder());
    }

    Optional<Response> render(@NonNull HandlerInput input, @NonNull final T data, @NonNull final ResponseBuilder builder);
}
