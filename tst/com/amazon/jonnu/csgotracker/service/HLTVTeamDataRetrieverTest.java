package com.amazon.jonnu.csgotracker.service;

import java.util.Locale;

import com.google.inject.Guice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.amazon.jonnu.csgotracker.injection.CSGOTrackerModule;
import com.amazon.jonnu.csgotracker.model.TeamRequest;
import com.amazon.jonnu.csgotracker.model.TeamRoster;
import com.amazon.jonnu.csgotracker.model.TeamSchedule;
import com.amazon.jonnu.csgotracker.storage.hltv.HLTVTeamDataRetriever;

class HLTVTeamDataRetrieverTest {

    private HLTVTeamDataRetriever fixture;

    @BeforeEach
    void before() {
        fixture = Guice.createInjector(new CSGOTrackerModule())
                .getInstance(HLTVTeamDataRetriever.class);
    }

    @Test
    void doTest() {
        TeamSchedule schedule = fixture.getTeamSchedule(createTeamRequest("Astralis"));
        System.out.println(schedule);
    }

    @Test
    void getRoster() {
        TeamRoster roster = fixture.getTeamRoster(createTeamRequest("astralis"));
        System.out.println(roster);
    }

    private TeamRequest createTeamRequest(final String teamName) {
        return TeamRequest.builder()
                .teamName(teamName)
                .locale(Locale.US)
                .build();
    }
}
