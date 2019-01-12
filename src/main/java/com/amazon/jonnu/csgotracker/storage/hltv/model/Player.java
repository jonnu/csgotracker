package com.amazon.jonnu.csgotracker.storage.hltv.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Player {
    private final Integer id;
    private final String name;
    private final String country;
}
