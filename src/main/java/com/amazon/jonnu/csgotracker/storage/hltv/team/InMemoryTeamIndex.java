package com.amazon.jonnu.csgotracker.storage.hltv.team;

import java.util.Map;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableBiMap;
import lombok.NonNull;

public class InMemoryTeamIndex implements TeamIdentifierStorage<Integer> {

    private final Map<String, Integer> teamMap = ImmutableBiMap.<String, Integer>builder()
            .put("astralis", 6665)
            .put("liquid", 5973)
            .put("natus vincere", 4608)
            .put("mibr", 9215)
            .put("faze", 6667)
            .put("mousesports", 4494)
            .put("nip", 4411)
            .put("nrg", 6673)
            .put("big", 7532)
            .put("north", 7533)
            .put("fnatic", 4991)
            .put("hellraisers", 5310)
            .put("renegades", 6211)
            .put("ence", 4869)
            .put("optic", 6615)
            .put("ldlc", 4674)
            .put("complexity", 5005)
            .put("ghost", 7801)
            .put("heroic", 7175)
            .put("avangar", 8120)
            .put("luminosity", 6290)
            .put("g2", 5995)
            .put("tyloo", 4863)
            .put("space soldiers", 9572)
            .put("vega squadron", 6094)
            .put("cloud9", 5752)
            .put("vitality", 9565)
            .put("bravado", 5158)
            .put("virtus.pro", 5378)
            .put("gambit", 6651)

            // extras
            .put("endpoint", 7234)
            .build();

    @Override
    @Nullable
    public Integer getIdentifier(@NonNull final String name) {
        return teamMap.get(normalize(name));
    }

    // this might need to move to somewhere else...
    private static String normalize(final String name) {
        return name.toLowerCase();
    }
}
