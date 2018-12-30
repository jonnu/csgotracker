package com.amazon.jonnu.csgotracker.model;

import java.util.List;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Team {
    @NonNull public final String spokenIdentifier;
    @NonNull public final String displayIdentifier;
    @NonNull public final List<Player> roster;
}
