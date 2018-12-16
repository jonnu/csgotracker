package com.amazon.jonnu.csgotracker.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TeamRequest {
    private final String teamName;
}
