package com.amazon.jonnu.csgotracker.storage.hltv.parser;

import com.amazon.jonnu.csgotracker.model.Player;
import com.amazon.jonnu.csgotracker.model.Team;
import org.jsoup.nodes.Document;

import java.util.stream.Collectors;

public class TeamRosterParser implements HLTVParser<Team> {

    @Override
    public Team parse(final Document document) {

        final String teamName = document.select(".profile-team-container .profile-team-name").text();

        return Team.builder()
                .spokenIdentifier(teamName)
                .displayIdentifier(teamName)
                .roster(document.select(".bodyshot-team > a").stream()
                        .map(element -> Player.builder()
                                .spokenIdentifier(element.attr("title"))
                                .displayIdentifier(element.attr("title"))
                                .country(element.selectFirst(".playerFlagName img").attr("title"))
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
