package com.amazon.jonnu.csgotracker.service;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Slot;

public interface EntityResolver {
    Optional<Slot> getSlotByName(final HandlerInput input, final String slotName);
}
