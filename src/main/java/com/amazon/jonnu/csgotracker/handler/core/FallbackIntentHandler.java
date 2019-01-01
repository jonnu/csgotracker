package com.amazon.jonnu.csgotracker.handler.core;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

public class FallbackIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(final HandlerInput input) {
        return input.matches(intentName("AMAZON.FallbackIntent"));
    }

    @Override
    public Optional<Response> handle(final HandlerInput input) {
        return input.getResponseBuilder()
                .withSpeech("Not sure.")
                .withSpeech("Still not sure.")
                .build();
    }
}
