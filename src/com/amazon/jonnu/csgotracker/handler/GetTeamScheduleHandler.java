package com.amazon.jonnu.csgotracker.handler;

import static com.amazon.ask.request.Predicates.intentName;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.google.inject.Inject;
import lombok.NonNull;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazon.jonnu.csgotracker.model.TeamScheduleResult;
import com.amazon.jonnu.csgotracker.service.CrappyScheduleInterface;
import com.amazon.jonnu.csgotracker.service.EntityResolver;

public class GetTeamScheduleHandler implements RequestHandler {

    private static final String SLOT_TEAM_IDENTIFIER = "TeamIdentifier";

    private final EntityResolver resolver;
    private final CrappyScheduleInterface storage;

    @Inject
    public GetTeamScheduleHandler(@NonNull final EntityResolver resolver, @NonNull final CrappyScheduleInterface storage) {
        this.resolver = resolver;
        this.storage = storage;
    }

    @Override
    public boolean canHandle(final HandlerInput input) {
        return input.matches(intentName("TeamScheduleIntent"));
    }

    @Override
    public Optional<Response> handle(final HandlerInput input) {

        final Optional<Slot> teamIdentifier = resolver.getSlotByName(input, SLOT_TEAM_IDENTIFIER);

        if (!teamIdentifier.isPresent()) {
            return Optional.empty();
        }

        TeamScheduleResult result = storage.getUpcomingMatches().get(0);

        final String speechString = String.format("%s will play %s at %s on %s",
                result.getQueriedTeam().getSpokenIdentifier(),
                result.getOpponentTeam().getSpokenIdentifier(),
                result.getDateTime().format(DateTimeFormatter.ISO_DATE_TIME),
                result.getMap()
        );

        return input.getResponseBuilder()
                .withSpeech(speechString)
                .withSimpleCard(String.format("Schedule for %s", teamIdentifier.get().getValue()), "It worked.")
                .build();
    }
}
