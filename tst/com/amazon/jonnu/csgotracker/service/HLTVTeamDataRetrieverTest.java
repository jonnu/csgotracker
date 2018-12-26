package com.amazon.jonnu.csgotracker.service;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.amazon.jonnu.csgotracker.model.TeamRequest;
import com.amazon.jonnu.csgotracker.model.TeamRoster;
import com.amazon.jonnu.csgotracker.model.TeamSchedule;
import com.amazon.jonnu.csgotracker.storage.hltv.HLTVDocumentParserFactoryImpl;
import com.amazon.jonnu.csgotracker.storage.hltv.HLTVTeamDataRetriever;
import com.amazon.jonnu.csgotracker.storage.hltv.InMemoryTeamIndex;

class HLTVTeamDataRetrieverTest {

    private HLTVTeamDataRetriever fixture;

    @BeforeEach
    void before() {
        fixture = new HLTVTeamDataRetriever(new InMemoryTeamIndex(), new HLTVDocumentParserFactoryImpl(Collections.emptyMap()));
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
