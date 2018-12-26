package com.amazon.jonnu.csgotracker.storage.hltv;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;

import com.amazon.jonnu.csgotracker.model.TeamRequest;
import com.amazon.jonnu.csgotracker.storage.ResourceMapper;

public class InMemoryTeamIndex implements ResourceMapper<Integer> {

    //final BKTreeMap<String, Integer> teamMap = new BKTreeMapImpl<>();

    private final Map<String, Integer> teamMap = ImmutableMap.<String, Integer>builder()
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
            .build();

    /**
     * Obtain a service-specific identifier for the given team.
     *
     * @param request A team request.
     * @return A team identifier.
     */
    @Override
    public Integer getResource(@NonNull final TeamRequest request) {
        return teamMap.get(request.getTeamName().toLowerCase());
    }

}
