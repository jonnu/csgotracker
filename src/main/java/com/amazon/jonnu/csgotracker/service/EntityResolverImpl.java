package com.amazon.jonnu.csgotracker.service;

import java.util.Optional;

import lombok.NonNull;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Slot;

public class EntityResolverImpl implements EntityResolver {

    @Override
    public Optional<Slot> getSlotByName(@NonNull final HandlerInput input, @NonNull final String slotName) {
        final IntentRequest request = (IntentRequest) input.getRequestEnvelope().getRequest();
        return Optional.ofNullable(request.getIntent().getSlots().get(slotName));
    }
}
