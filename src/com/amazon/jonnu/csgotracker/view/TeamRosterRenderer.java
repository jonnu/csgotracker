package com.amazon.jonnu.csgotracker.view;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import lombok.NonNull;

import com.amazon.ask.model.Response;
import com.amazon.ask.model.ui.Card;
import com.amazon.ask.model.ui.SimpleCard;
import com.amazon.ask.response.ResponseBuilder;
import com.amazon.jonnu.csgotracker.model.RosteredRole;
import com.amazon.jonnu.csgotracker.model.TeamRoster;
import com.amazon.jonnu.csgotracker.service.I18nStringProvider;
import com.amazon.jonnu.csgotracker.service.I18nStringProviderFactory;

public class TeamRosterRenderer implements Renderer<TeamRoster> {

    private final I18nStringProviderFactory stringProviderFactory;

    private I18nStringProvider stringProvider;

    @Inject
    public TeamRosterRenderer(@NonNull final I18nStringProviderFactory stringProviderFactory) {
        this.stringProviderFactory = stringProviderFactory;
        this.stringProvider = stringProviderFactory.create(Locale.US);
    }

    @Override
    public Optional<Response> render(@NonNull final TeamRoster data, @NonNull final ResponseBuilder builder) {
        return builder.withSpeech(renderSpeech(data))
                .withCard(renderCard(data))
                .build();
    }

    private String renderSpeech(final TeamRoster data) {

        Map<String, String> variables = ImmutableMap.<String, String>builder()
                .put("teamName", data.getTeamName())
                .put("individuals", data.getIndividuals()
                        .stream()
                        .filter(individual -> individual.getRoles().contains(RosteredRole.PLAYER))
                        .map(individual -> individual.getIndividual().getAlias())
                        .collect(Collectors.joining(", ")))
                .build();

        return stringProvider.getString("roster.team", Locale.US, variables);
    }

    private Card renderCard(final TeamRoster data) {
        return SimpleCard.builder()
                .withTitle(data.toString())
                .withContent(data.toString())
                .build();
    }
}
