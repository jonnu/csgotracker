package com.amazon.jonnu.csgotracker.service;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.amazon.jonnu.csgotracker.model.Player;
import com.amazon.jonnu.csgotracker.model.TeamScheduleResult;
import com.amazon.jonnu.csgotracker.service.jsoup.ConnectionFactoryImpl;

class HLTVTest {

    private HLTV fixture;

    @BeforeEach
    void before() {
        fixture = new HLTV(new ConnectionFactoryImpl());
    }

    @Test
    void doTest() {
        List<TeamScheduleResult> results = fixture.getUpcomingMatches();
        results.forEach(System.out::println);
    }

    @Test
    void getRoster() {
        List<Player> players = fixture.getRoster();
        players.forEach(System.out::println);
    }
}
