package com.amazon.jonnu.csgotracker.model;

import lombok.Builder;
import lombok.Value;

import java.util.Collections;
import java.util.List;

@Value
@Builder
public class MapList {

    private static final String BEST_OF_PREFIX = "bo";

    private final List<Map> maps;

    public static MapList create(final String mapString) {

        if (mapString.substring(0, BEST_OF_PREFIX.length()).equalsIgnoreCase(BEST_OF_PREFIX)) {
            int bestOf = Integer.parseInt(mapString.substring(BEST_OF_PREFIX.length()));
            return MapList.builder()
                    .maps(Collections.nCopies(bestOf, Map.UNKNOWN))
                    .build();
        }

        return MapList.builder()
                .maps(Collections.singletonList(Map.resolveFromShorthand(mapString)))
                .build();
    }
}
