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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.amazon.jonnu.csgotracker.model.Team;
import com.amazon.jonnu.csgotracker.model.TeamScheduleResult;

public class HLTV implements CrappyScheduleInterface {

    public List<TeamScheduleResult> getUpcomingMatches() {

        try {

            // All Astralis matches.
            final Document document = Jsoup.connect("https://www.hltv.org/matches?team=6665").get();
            return document.select(".matches .upcoming-matches a").stream()
                    .map(element -> {
                        Elements teamElements = element.select("td.team-cell > .team");
                        return TeamScheduleResult.builder()
                                .queriedTeam(Team.builder().spokenIdentifier(teamElements.get(0).text()).displayIdentifier(teamElements.get(0).text()).build())
                                .opponentTeam(Team.builder().spokenIdentifier(teamElements.get(1).text()).displayIdentifier(teamElements.get(1).text()).build())
                                .scheduledDateTime(convertEpochToLocalDateTime(element.selectFirst("td.time > .time").text()))
                                .build();
                    })
                    .collect(Collectors.toList());

        } catch (IOException exception) {
            return Collections.emptyList();
        }

    }

    private static ZonedDateTime convertEpochToLocalDateTime(final String epochString) {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(epochString)), ZoneOffset.UTC);
    }
}
