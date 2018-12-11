package com.amazon.jonnu.csgotracker.service;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.amazon.jonnu.csgotracker.model.Player;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.amazon.jonnu.csgotracker.model.Map;
import com.amazon.jonnu.csgotracker.model.Team;
import com.amazon.jonnu.csgotracker.model.TeamScheduleResult;

@Slf4j
public class HLTV implements CrappyScheduleInterface {

    public List<TeamScheduleResult> getUpcomingMatches() {

        try {
            // All Astralis matches.
            final Document document = Jsoup.connect("https://www.hltv.org/matches?team=6665")
                    .method(Connection.Method.GET)
                    .userAgent("github.com/jonnu/csgotracker; Alexa Skill; jonnu <jon@ellis-jones.org>")
                    .followRedirects(true)
                    .get();

            return document.select(".matches .upcoming-matches a").stream()
                    //.peek(System.out::println)
                    .map(element -> {
                        Elements teamElements = element.select("td.team-cell .team");
                        return TeamScheduleResult.builder()
                                .queriedTeam(Team.builder().spokenIdentifier(teamElements.get(0).text()).displayIdentifier(teamElements.get(0).text()).build())
                                .opponentTeam(Team.builder().spokenIdentifier(teamElements.get(1).text()).displayIdentifier(teamElements.get(1).text()).build())
                                .dateTime(convertEpochToLocalDateTime(element.selectFirst("td.time > .time").attr("data-unix")))
                                .map(Map.resolveFromShorthand(element.selectFirst("td.star-cell .map-text").text()))
                                .build();
                    })
                    .collect(Collectors.toList());

        } catch (IOException exception) {
            log.error("err", exception);
            return Collections.emptyList();
        }
    }

    public List<Player> getRoster() {

        try {
            // All Astralis players.
            final Document document = Jsoup.connect("https://www.hltv.org/team/6665/astralis")
                    .method(Connection.Method.GET)
                    .userAgent("github.com/jonnu/csgotracker; Alexa Skill; jonnu <jon@ellis-jones.org>")
                    .followRedirects(true)
                    .get();

            return document.select(".bodyshot-team > a").stream()
                    .map(element -> Player.builder()
                            .spokenIdentifier(element.attr("title"))
                            .displayIdentifier(element.attr("title"))
                            .build())
                    .collect(Collectors.toList());

        } catch (IOException exception) {
            log.error("err", exception);
            return Collections.emptyList();
        }
    }

    private static ZonedDateTime convertEpochToLocalDateTime(final String epochString) {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(epochString)), ZoneOffset.UTC);
    }
}
