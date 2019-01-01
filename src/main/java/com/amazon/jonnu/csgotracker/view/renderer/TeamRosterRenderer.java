package com.amazon.jonnu.csgotracker.view.renderer;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.LocaleUtils;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.ui.Card;
import com.amazon.ask.model.ui.StandardCard;
import com.amazon.ask.response.ResponseBuilder;
import com.amazon.jonnu.csgotracker.model.RosteredRole;
import com.amazon.jonnu.csgotracker.model.TeamRoster;
import com.amazon.jonnu.csgotracker.service.IterableTemplateVariable;
import com.amazon.jonnu.csgotracker.service.SimpleTemplateVariable;
import com.amazon.jonnu.csgotracker.service.TemplateReplacer;
import com.amazon.jonnu.csgotracker.service.TemplateVariable;
import com.amazon.jonnu.csgotracker.service.i18n.I18NStringResolver;

@Slf4j
public class TeamRosterRenderer implements Renderer<TeamRoster> {

    private final I18NStringResolver i18nStringResolver;

    private TemplateReplacer replacer;

    @Inject
    public TeamRosterRenderer(@NonNull final I18NStringResolver i18nStringResolver, @NonNull final TemplateReplacer replacer) {
        this.i18nStringResolver = i18nStringResolver;
        this.replacer = replacer;
    }

    @Override
    public Optional<Response> render(@NonNull final HandlerInput input, @NonNull final TeamRoster data, @NonNull final ResponseBuilder builder) {

        Locale requestLocale = parseLocale(input.getRequestEnvelope().getRequest().getLocale());

        return builder.withSpeech(renderSpeech(data, requestLocale))
                .withCard(renderCard(data, requestLocale))
                .build();
    }

    private String renderSpeech(final TeamRoster data, final Locale locale) {

        Map<String, String> variables = ImmutableMap.<String, String>builder()
                .put("team", data.getTeamName())
                .put("individuals", data.getIndividuals()
                        .stream()
                        .filter(individual -> individual.getRoles().contains(RosteredRole.PLAYER))
                        .map(individual -> individual.getIndividual().getAlias())
                        .collect(Collectors.joining(", ")))
                .build();

        String template = i18nStringResolver.setLocale(locale)
                .resolve("roster.team", variables);

        final List<String> individuals = data.getIndividuals()
                .stream()
                .filter(individual -> individual.getRoles().contains(RosteredRole.PLAYER))
                .map(individual -> individual.getIndividual().getAlias()).collect(Collectors.toList());

        Map<String, TemplateVariable> replacements = ImmutableMap.<String, TemplateVariable>builder()
                .put("team", SimpleTemplateVariable.builder().key("teamName").variable(data.getTeamName()).build())
                .put("individuals", IterableTemplateVariable.builder().key("individuals").variable(individuals).build())
                .build();

        log.error("Template: {}", template);
        return replacer.replace(template, replacements);
    }

    private Card renderCard(final TeamRoster data, final Locale locale) {

        /*
        Map<String, TemplateVariable> replacements = ImmutableMap.<String, TemplateVariable>builder()
                .put("team", SimpleTemplateVariable.builder().key("teamName").variable(data.getTeamName()).build())
                //.put("individuals", IterableTemplateVariable.builder().key("individuals").variable(individuals).build())
                .build();
        */
        Map<String, String> replacements = Collections.emptyMap();

        String title = i18nStringResolver.setLocale(locale)
                .resolve("roster.card.title", replacements);

        return StandardCard.builder()
                //.withImage(Image.builder()
                //        .withLargeImageUrl()
                //        .withSmallImageUrl()
                //        .build())
                .withTitle(title)
                .withText("Roster TBC")
                .build();
    }

    private Locale parseLocale(final String locale) {
        return locale.contains("-") ? Locale.forLanguageTag(locale) : LocaleUtils.toLocale(locale);
    }
}
