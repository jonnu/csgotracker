package com.amazon.jonnu.csgotracker.handler.core;

import static com.amazon.jonnu.csgotracker.service.Predicates.intentNameOneOf;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

public class CancelAndStopIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(final HandlerInput input) {
        return input.matches(intentNameOneOf("AMAZON.StopIntent", "AMAZON.CancelIntent", "AMAZON.NoIntent"));
    }

    @Override
    public Optional<Response> handle(final HandlerInput input) {
        return input.getResponseBuilder()
                .withSpeech("Goodbye")
                .build();
    }
}
