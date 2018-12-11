package com.amazon.jonnu.csgotracker.model;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum Map {

    UNKNOWN(""),
    DE_DUST2("d2"),
    DE_CACHE("cch"),
    DE_NUKE("nuke"),
    DE_TRAIN("trn"),
    DE_CBBLE("cbl"),
    DE_MIRAGE("mrg"),
    DE_INFERNO("inf"),
    DE_OVERPASS("ovp");

    private final String shorthand;

    Map(final String shorthand) {
        this.shorthand = shorthand.toLowerCase();
    }

    public static Map resolveFromShorthand(final String shorthand) {
        return Arrays.stream(values())
                .filter(code -> code.getShorthand().equals(shorthand.toLowerCase()))
                .findFirst()
                .orElse(Map.UNKNOWN);
    }
}
