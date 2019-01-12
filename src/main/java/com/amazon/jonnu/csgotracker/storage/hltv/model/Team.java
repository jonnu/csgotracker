package com.amazon.jonnu.csgotracker.storage.hltv.model;

import java.util.List;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Team {
    private final Integer id;
    private final String name;
    private final String country;
    private final List<Player> roster;
}
