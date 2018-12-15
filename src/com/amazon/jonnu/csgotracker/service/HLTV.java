package com.amazon.jonnu.csgotracker.service;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.inject.Inject;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.amazon.jonnu.csgotracker.model.Map;
import com.amazon.jonnu.csgotracker.model.Player;
import com.amazon.jonnu.csgotracker.model.Team;
import com.amazon.jonnu.csgotracker.model.TeamScheduleResult;
import com.amazon.jonnu.csgotracker.service.jsoup.ConnectionFactory;

@Slf4j
public class HLTV implements CrappyScheduleInterface {

    private final ConnectionFactory connectionFactory;

    @Inject
    public HLTV(@NonNull final ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public List<TeamScheduleResult> getUpcomingMatches(final int teamIdentifier) {

        try {

            // All Astralis matches.
            final Document document = connectionFactory.getConnection("https://www.hltv.org/matches?team=" + teamIdentifier)
                    .get();

            log.error(document.baseUri());

            return document.select(".matches .upcoming-matches a").stream()
                    //.peek(System.out::println)
                    .map(element -> {
                        Elements teamElements = element.select("td.team-cell .team");
                        return TeamScheduleResult.builder()
                                .queriedTeam(Team.builder().spokenIdentifier(teamElements.get(0).text()).displayIdentifier(teamElements.get(0).text()).roster(Collections.emptyList()).build())
                                .opponentTeam(Team.builder().spokenIdentifier(teamElements.get(1).text()).displayIdentifier(teamElements.get(1).text()).roster(Collections.emptyList()).build())
                                .dateTime(convertEpochToZonedDateTime(element.selectFirst("td.time > .time").attr("data-unix")))
                                .map(resolveMapList(element.selectFirst("td.star-cell .map-text").text()))
                                .build();
                    })
                    .collect(Collectors.toList());

        } catch (IOException exception) {
            log.error("err", exception);
            return Collections.emptyList();
        }
    }

    private List<Map> resolveMapList(final String mapText) {

        if (mapText.startsWith("bo")) {
            return IntStream.rangeClosed(1, Integer.parseInt(mapText.substring(2)))
                    .boxed()
                    .map(x -> Map.UNKNOWN)
                    .collect(Collectors.toList());
        }

        return Collections.singletonList(Map.resolveFromShorthand(mapText));
    }

    public List<Player> getRoster() {

        try {

            // All Astralis players.
            final Document document = connectionFactory.getConnection("https://www.hltv.org/team/6665/astralis")
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

    private static ZonedDateTime convertEpochToZonedDateTime(final String epochString) {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(epochString)), ZoneOffset.UTC);
    }
}
