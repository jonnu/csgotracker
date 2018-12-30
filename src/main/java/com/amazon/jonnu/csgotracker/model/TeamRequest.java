package com.amazon.jonnu.csgotracker.model;

import java.util.Locale;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TeamRequest {
    private final Locale locale;
    private final String teamName;
}
