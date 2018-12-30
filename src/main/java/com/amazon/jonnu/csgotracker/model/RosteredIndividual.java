package com.amazon.jonnu.csgotracker.model;

import java.util.Set;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RosteredIndividual {

    private Individual individual;
    private Set<RosteredRole> roles;
    private boolean active;
}
