package com.amazon.jonnu.csgotracker.model;

import lombok.Builder;
import lombok.NonNull;

@Builder
public class Person {
    @NonNull public final String realName;
}
