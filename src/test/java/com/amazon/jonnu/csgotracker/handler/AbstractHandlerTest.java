package com.amazon.jonnu.csgotracker.handler;

import java.time.OffsetDateTime;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.Slot;

abstract class AbstractHandlerTest {

    HandlerInput getIntentRequestHandlerInput(final String intentName) {
        return getIntentRequestHandlerInput(intentName, null);
    }

    HandlerInput getIntentRequestHandlerInput(final String intentName, final Map<String, Slot> slots) {
        return HandlerInput.builder()
                .withRequestEnvelope(RequestEnvelope.builder()
                        .withRequest(IntentRequest.builder()
                                .withIntent(Intent.builder()
                                        .withName(intentName)
                                        .withSlots(slots)
                                        .build())
                                .withLocale("en-US")
                                .withTimestamp(OffsetDateTime.now())
                                .build())
                        .build())
                .build();
    }

    Map<String, Slot> getTeamIdentifierSlotMap(final String teamIdentifier) {
        return ImmutableMap.<String, Slot>builder()
                .put("TeamIdentifier", Slot.builder()
                        .withName("TeamIdentifier")
                        .withValue(teamIdentifier)
                        .build())
                .build();
    }
}
