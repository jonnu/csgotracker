package com.amazon.jonnu.csgotracker.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class GetTeamScheduleHandler implements RequestHandler {

    private static final String SLOT_TEAM_IDENTIFIER = "TeamIdentifier";

    @Override
    public boolean canHandle(final HandlerInput input) {
        return input.matches(intentName("TeamScheduleIntent"));
    }

    @Override
    public Optional<Response> handle(final HandlerInput input) {

        final Slot teamIdentifier = getSlotFromInput(input, SLOT_TEAM_IDENTIFIER);

        return input.getResponseBuilder()
                .withSpeech(String.format("Testing 1 2 3 - %s", teamIdentifier.getValue()))
                .withSimpleCard(String.format("Schedule for %s", teamIdentifier.getValue()), "It worked.")
                .build();
    }

    private static Slot getSlotFromInput(final HandlerInput input, final String slotName) {
        return ((IntentRequest) input.getRequestEnvelope().getRequest())
                .getIntent()
                .getSlots()
                .get(slotName);
    }
}
