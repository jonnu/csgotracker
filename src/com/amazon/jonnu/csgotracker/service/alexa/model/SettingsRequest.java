package com.amazon.jonnu.csgotracker.service.alexa.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SettingsRequest {
    private final String endpoint;
    private final String apiToken;
    private final String deviceId;
}
