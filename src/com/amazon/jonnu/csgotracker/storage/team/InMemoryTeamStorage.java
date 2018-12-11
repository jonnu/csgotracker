package com.amazon.jonnu.csgotracker.storage.team;

import com.amazon.jonnu.csgotracker.model.Team;

public class InMemoryTeamStorage implements TeamStorage {

    @Override
    public Team getTeam(final String teamIdentifier) {
        return Team.builder()
                .spokenIdentifier("Astralis")
                .displayIdentifier("Astralis")
                .build();
    }
}
