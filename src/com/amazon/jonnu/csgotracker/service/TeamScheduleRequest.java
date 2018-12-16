package com.amazon.jonnu.csgotracker.service;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TeamScheduleRequest {
    private final IntentRequest intentRequest;
}
