package com.amazon.jonnu.csgotracker.storage.hltv.parser;

import java.util.stream.Collectors;

import com.google.common.collect.ImmutableSet;
import org.jsoup.nodes.Document;

import com.amazon.jonnu.csgotracker.model.Individual;
import com.amazon.jonnu.csgotracker.model.RosteredIndividual;
import com.amazon.jonnu.csgotracker.model.RosteredRole;
import com.amazon.jonnu.csgotracker.model.TeamRoster;

public class TeamRosterParser implements HLTVDocumentParser<TeamRoster> {

    @Override
    public TeamRoster parse(final Document document) {

        final String teamName = document.select(".profile-team-container .profile-team-name").text();

        return TeamRoster.builder()
                .teamName(teamName)
                //.spokenIdentifier(teamName)
                //.displayIdentifier(teamName)
                .individuals(document.select(".bodyshot-team > a").stream()
                        .map(element -> Individual.builder()
                                .alias(element.attr("title"))
                                .country(element.selectFirst(".playerFlagName img").attr("title"))
                                .build())
                        .collect(Collectors.toList())
                        .stream()
                        .map(individual -> RosteredIndividual.builder()
                                .active(true)
                                .individual(individual)
                                .roles(ImmutableSet.of(RosteredRole.PLAYER))
                                .build())
                        .collect(Collectors.toList())
                )
//                .roster(document.select(".bodyshot-team > a").stream()
//                        .map(element -> Player.builder()
//                                .spokenIdentifier(element.attr("title"))
//                                .displayIdentifier(element.attr("title"))
//                                .country(element.selectFirst(".playerFlagName img").attr("title"))
//                                .build())
//                        .collect(Collectors.toList()))
                .build();
    }
}
