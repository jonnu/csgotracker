package com.amazon.jonnu.csgotracker.storage.hltv.parser;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableSet;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.amazon.jonnu.csgotracker.model.Individual;
import com.amazon.jonnu.csgotracker.model.RosteredIndividual;
import com.amazon.jonnu.csgotracker.model.RosteredRole;
import com.amazon.jonnu.csgotracker.model.TeamRoster;
import com.amazon.jonnu.csgotracker.storage.hltv.model.Player;
import com.amazon.jonnu.csgotracker.storage.hltv.model.Team;

public class TeamRosterParser implements HLTVDocumentParser<TeamRoster> {

    @Override
    public TeamRoster parse(final Document document) {

        Element profile = document.selectFirst(".teamProfile");

        // @TODO - split 'HLTV' models and CSGOTracker models into two.
        // decouple these two layers, basically.
        Team team = Team.builder()
                .id(getIdentifierFromURL(profile.selectFirst("img.teamlogo").attr("src")))
                .name(profile.selectFirst(".profile-team-name").text())
                .country(getCountryCodeFromURL(profile.selectFirst(".team-country img.flag").attr("src")))
                .roster(profile.select(".bodyshot-team > a").stream()
                        .map(member -> Player.builder()
                                .id(getIdentifierFromURL(member.attr("href"), 1))
                                .name(member.attr("title"))
                                .country(getCountryCodeFromURL(member.selectFirst("img.flag").attr("src")))
                                .build())
                        .collect(Collectors.toList()))
                .build();

        return TeamRoster.builder()
                .teamName(profile.selectFirst(".profile-team-name").text())
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
                .build();
    }

    private String getCountryCodeFromURL(final String url) {
        return url.substring(url.length() - 6, url.length() - 4).toUpperCase();
    }

    private Integer getIdentifierFromURL(final String url) {
        return getIdentifierFromURL(url, 0);
    }

    private Integer getIdentifierFromURL(final String url, int position) {

        List<String> pieces = Arrays.asList(url.split("/"));
        if (pieces.isEmpty()) {
            return null;
        }

        ListIterator<String> iterator = pieces.listIterator(pieces.size());
        while (position > 0 && iterator.hasPrevious()) {
            iterator.previous();
            position--;
        }

        try {
            return Integer.parseInt(iterator.previous());
        }
        catch (NumberFormatException exception) {
            return null;
        }
    }
}
