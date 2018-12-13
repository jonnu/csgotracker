package com.amazon.jonnu.csgotracker.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Player extends Person {
    @NonNull public final String spokenIdentifier;
    @NonNull public final String displayIdentifier;

}
