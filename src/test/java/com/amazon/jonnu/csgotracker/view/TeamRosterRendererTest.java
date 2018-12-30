package com.amazon.jonnu.csgotracker.view;

import com.amazon.ask.model.Response;
import com.amazon.jonnu.csgotracker.model.Individual;
import com.amazon.jonnu.csgotracker.model.RosteredIndividual;
import com.amazon.jonnu.csgotracker.model.RosteredRole;
import com.amazon.jonnu.csgotracker.model.TeamRoster;
import com.amazon.jonnu.csgotracker.service.I18nStringProviderImpl;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class TeamRosterRendererTest {

    private TeamRosterRenderer fixture;

    @BeforeEach
    void before() {
        fixture = new TeamRosterRenderer(I18nStringProviderImpl::new);
    }

    @Test
    void render() {
        Optional<Response> response = fixture.render(getSampleData());
        System.out.println(response.get());
    }

    private TeamRoster getSampleData() {
        return TeamRoster.builder()
                .teamName("Asperity")
                .individuals(ImmutableList.of(
                        createPlayer("jonte"),
                        createPlayer("silly"),
                        createPlayer("goose"),
                        createPlayer("ewaf"),
                        createPlayer("fYm")
                ))
                .build();
    }

    private RosteredIndividual createPlayer(final String alias) {
        return createPlayer(alias, null, null, "GB");
    }

    private RosteredIndividual createPlayer(final String alias, final String firstName, final String lastName, final String country) {
        return RosteredIndividual.builder()
                .active(true)
                .roles(ImmutableSet.of(RosteredRole.PLAYER))
                .individual(Individual.builder()
                        .alias(alias)
                        .firstName(firstName)
                        .lastName(lastName)
                        .country(country)
                        .build())
                .build();
    }
}