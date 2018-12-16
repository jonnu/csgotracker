package com.amazon.jonnu.csgotracker.service;

import com.amazon.jonnu.csgotracker.model.Team;
import com.amazon.jonnu.csgotracker.model.TeamScheduleResult;
import com.amazon.jonnu.csgotracker.service.jsoup.ConnectionFactoryImpl;
import com.amazon.jonnu.csgotracker.storage.hltv.HLTVTeamDataRetriever;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class HLTVTeamDataRetrieverTest {

    private HLTVTeamDataRetriever fixture;

    @BeforeEach
    void before() {
        fixture = new HLTVTeamDataRetriever(new ConnectionFactoryImpl());
    }

    @Test
    void doTest() {
        List<TeamScheduleResult> results = fixture.getUpcomingMatches(7801);
        results.forEach(System.out::println);
    }

    @Test
    void getRoster() {
        Team team = fixture.getRoster(7801).orElse(null);
        team.getRoster().forEach(System.out::println);
    }
}
