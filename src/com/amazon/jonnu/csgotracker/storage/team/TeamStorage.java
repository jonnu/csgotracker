package com.amazon.jonnu.csgotracker.storage.team;

import com.amazon.jonnu.csgotracker.model.Team;

public interface TeamStorage {
    Team getTeam(final String teamIdentifier);
}
