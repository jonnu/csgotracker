package com.amazon.jonnu.csgotracker.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Individual {
    private String alias;
    private String firstName;
    private String lastName;
    private String country;
}
