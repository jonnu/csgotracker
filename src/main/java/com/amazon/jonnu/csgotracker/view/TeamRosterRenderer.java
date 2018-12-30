package com.amazon.jonnu.csgotracker.view;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import com.amazon.ask.model.Response;
import com.amazon.ask.model.ui.Card;
import com.amazon.ask.model.ui.StandardCard;
import com.amazon.ask.response.ResponseBuilder;
import com.amazon.jonnu.csgotracker.model.RosteredRole;
import com.amazon.jonnu.csgotracker.model.TeamRoster;
import com.amazon.jonnu.csgotracker.service.I18nStringProvider;
import com.amazon.jonnu.csgotracker.service.I18nStringProviderFactory;
import com.amazon.jonnu.csgotracker.service.IterableTemplateVariable;
import com.amazon.jonnu.csgotracker.service.SimpleTemplateVariable;
import com.amazon.jonnu.csgotracker.service.TemplateStringThing;
import com.amazon.jonnu.csgotracker.service.TemplateVariable;

@Slf4j
public class TeamRosterRenderer implements Renderer<TeamRoster> {

    private final I18nStringProviderFactory stringProviderFactory;

    private I18nStringProvider stringProvider;
    private TemplateStringThing thingy;

    @Inject
    public TeamRosterRenderer(@NonNull final I18nStringProviderFactory stringProviderFactory) {
        this.stringProviderFactory = stringProviderFactory;
        this.stringProvider = stringProviderFactory.create(Locale.US);

        this.thingy = new TemplateStringThing();
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

        String template = stringProvider.getString("roster.team", Locale.US, variables);

        final List<String> individuals = data.getIndividuals()
                .stream()
                .filter(individual -> individual.getRoles().contains(RosteredRole.PLAYER))
                .map(individual -> individual.getIndividual().getAlias()).collect(Collectors.toList());

        Map<String, TemplateVariable> replacements = ImmutableMap.<String, TemplateVariable>builder()
                .put("team", SimpleTemplateVariable.builder().key("teamName").variable(data.getTeamName()).build())
                .put("individuals", IterableTemplateVariable.builder().key("individuals").variable(individuals).build())
                .build();

        log.error("Template: {}", template);
        return thingy.replace(template, replacements);
    }

    private Card renderCard(final TeamRoster data) {

        Map<String, TemplateVariable> replacements = ImmutableMap.<String, TemplateVariable>builder()
                .put("team", SimpleTemplateVariable.builder().key("teamName").variable(data.getTeamName()).build())
                //.put("individuals", IterableTemplateVariable.builder().key("individuals").variable(individuals).build())
                .build();

        String title = stringProvider.getString("roster.card.title", Locale.US, replacements);

        return StandardCard.builder()
                //.withImage(Image.builder()
                //        .withLargeImageUrl()
                //        .withSmallImageUrl()
                //        .build())
                .withTitle(title)
                .withText("Roster TBC")
                .build();
    }
}
