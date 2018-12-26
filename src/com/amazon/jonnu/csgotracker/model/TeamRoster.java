package com.amazon.jonnu.csgotracker.model;

import java.util.List;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class TeamRoster implements Renderable {
    @NonNull private String teamName;
    @NonNull private List<RosteredIndividual> individuals;
}
