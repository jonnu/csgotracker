package com.amazon.jonnu.csgotracker.model;

import java.util.Arrays;
import java.util.function.Predicate;

import lombok.Getter;

@Getter
public enum Map {

    UNKNOWN("", ""),
    DE_DUST2("d2", "dust two"),
    DE_CACHE("cch", "cache"),
    DE_NUKE("nuke", "nuke"),
    DE_TRAIN("trn", "train"),
    DE_CBBLE("cbl", "cobblestone"),
    DE_MIRAGE("mrg", "mirage"),
    DE_INFERNO("inf", "inferno"),
    DE_OVERPASS("ovp", "overpass");

    private final String shorthand;
    private final String spokenIdentifier;

    Map(final String shorthand, final String spokenIdentifier) {
        this.shorthand = shorthand.toLowerCase();
        this.spokenIdentifier = spokenIdentifier;
    }

    public String getDisplayIdentifier() {
        return name().toLowerCase();
    }

    public static Map resolveFromName(final String name) {
        return filterMapPool(map -> map.name().equalsIgnoreCase(name));
    }

    public static Map resolveFromShorthand(final String shorthand) {
        return filterMapPool(map -> map.getShorthand().equals(shorthand.toLowerCase()));
    }

    private static Map filterMapPool(final Predicate<Map> filteringPredicate) {
        return Arrays.stream(values())
                .filter(filteringPredicate)
                .findFirst()
                .orElse(Map.UNKNOWN);
    }
}
