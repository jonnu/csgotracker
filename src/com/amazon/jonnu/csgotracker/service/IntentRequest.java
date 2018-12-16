package com.amazon.jonnu.csgotracker.service;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Slot;
import com.amazon.ask.model.interfaces.system.SystemState;
import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Value
@Builder
public class IntentRequest {

    private final String apiToken;
    private final String apiEndpoint;
    private final String deviceId;

    private final String intentName;
    private final Map<String, Slot> intentSlots;

    public static IntentRequest buildFromInput(final HandlerInput input) {

        if (!(input.getRequestEnvelope().getRequest() instanceof com.amazon.ask.model.IntentRequest)) {
            throw new IllegalArgumentException("Input was not an IntentRequest.");
        }

        final SystemState systemState = input.getRequestEnvelope().getContext().getSystem();
        final com.amazon.ask.model.IntentRequest intentRequest = ((com.amazon.ask.model.IntentRequest) input.getRequestEnvelope().getRequest());

        return IntentRequest.builder()
                .apiToken(systemState.getApiAccessToken())
                .apiEndpoint(systemState.getApiEndpoint())
                .deviceId(systemState.getDevice().getDeviceId())
                .intentName(intentRequest.getIntent().getName())
                .intentSlots(intentRequest.getIntent().getSlots())
                .build();
    }
}
