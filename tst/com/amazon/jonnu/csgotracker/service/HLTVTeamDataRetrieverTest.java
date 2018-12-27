package com.amazon.jonnu.csgotracker.service;

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
//        fixture = new HLTVTeamDataRetriever(
//                new HLTVDataDelegateImpl()new InMemoryTeamIndex(), new HLTVDocumentParserFactoryImpl(Collections.emptyMap()));
    }

    @Test
    void doTest() {
        TeamSchedule schedule = fixture.getTeamSchedule(TeamRequest.builder().build());
        System.out.println(schedule);
    }

    @Test
    void getRoster() {
        TeamRoster roster = fixture.getTeamRoster(TeamRequest.builder().build());
        System.out.println(roster);
    }
}
