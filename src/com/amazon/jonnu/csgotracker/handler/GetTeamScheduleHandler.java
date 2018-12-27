package com.amazon.jonnu.csgotracker.handler;

import static com.amazon.ask.request.Predicates.intentName;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;

import com.google.inject.Inject;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.ocpsoft.prettytime.PrettyTime;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazon.jonnu.csgotracker.model.TeamScheduleResult;
import com.amazon.jonnu.csgotracker.service.EntityResolver;
import com.amazon.jonnu.csgotracker.service.IntentRequest;
import com.amazon.jonnu.csgotracker.service.alexa.AlexaSettings;
import com.amazon.jonnu.csgotracker.service.alexa.model.SettingsRequest;
import com.amazon.jonnu.csgotracker.storage.TeamDataRetriever;
import com.amazon.jonnu.csgotracker.storage.hltv.team.TeamIdentifierStorage;

@Slf4j
public class GetTeamScheduleHandler implements RequestHandler {

    private static final String SLOT_TIME_INDICATOR = "TimeIndicator";
    private static final String SLOT_TEAM_IDENTIFIER = "TeamIdentifier";

    private static final PrettyTime prettyTime = new PrettyTime();

    private final EntityResolver resolver;
    private final TeamDataRetriever storage;
    private final AlexaSettings alexaSettings;
    private final TeamIdentifierStorage<Integer> identifierStorage;

    @Inject
    public GetTeamScheduleHandler(@NonNull final EntityResolver resolver, @NonNull final TeamDataRetriever storage, @NonNull final AlexaSettings alexaSettings, @NonNull final TeamIdentifierStorage<Integer> identifierStorage) {
        this.resolver = resolver;
        this.storage = storage;
        this.alexaSettings = alexaSettings;
        this.identifierStorage = identifierStorage;
    }

    @Override
    public boolean canHandle(final HandlerInput input) {
        return input.matches(intentName("TeamScheduleIntent"));
    }

    @Override
    public Optional<Response> handle(final HandlerInput input) {

        IntentRequest intentRequest = IntentRequest.buildFromInput(input);




        TimeZone timeZone = alexaSettings.getTimeZone(SettingsRequest.builder()
                .apiToken(input.getRequestEnvelope().getContext().getSystem().getApiAccessToken())
                .endpoint(input.getRequestEnvelope().getContext().getSystem().getApiEndpoint())
                .deviceId(input.getRequestEnvelope().getContext().getSystem().getDevice().getDeviceId())
                .build())
                .orElse(TimeZone.getDefault());

        log.info("Resolved timeZone: {}", timeZone.getDisplayName());

        final Optional<Slot> teamIdentifier = resolver.getSlotByName(input, SLOT_TEAM_IDENTIFIER);

        if (!teamIdentifier.isPresent()) {
            return Optional.empty();
        }

        log.info("Resolved team as {}", teamIdentifier.get());

        // Map team to hltv ID.
        Optional<Integer> hltvIdentifier = Optional.ofNullable(identifierStorage.getIdentifier(teamIdentifier.get().getValue()));
        if (!hltvIdentifier.isPresent()) {
            log.error("Could not resolve {} to an identifier.", teamIdentifier.get().getValue());
            return input.getResponseBuilder()
                    .withReprompt("Could not understand team " + teamIdentifier.get().getValue())
                    .build();
        }

        log.info("Calling storage for team with numerical identifier {}", hltvIdentifier.get());
        List<TeamScheduleResult> upcoming = Collections.emptyList();
        //storage.getUpcomingMatches(hltvIdentifier.get());

        if (upcoming.isEmpty()) {
            return input.getResponseBuilder()
                    .withSpeech("No upcoming matches found.")
                    .build();
        }

        TeamScheduleResult result = upcoming.get(0);

        final String speechString = String.format("%s will play %s in %s on %s",
                result.getQueriedTeam().getSpokenIdentifier(),
                result.getOpponentTeam().getSpokenIdentifier(),
                getPrettyTime(result.getDateTime(), timeZone),
                result.getMapList().getMaps().get(0).getSpokenIdentifier()
        );

        return input.getResponseBuilder()
                .withSpeech(speechString)
                .withSimpleCard(String.format("Schedule for %s", teamIdentifier.get().getValue()), "It worked.")
                .build();
    }

    private String getPrettyTime(final ZonedDateTime dateTime, final TimeZone timeZone) {
        return prettyTime.setLocale(Locale.US)
                .setReference(Calendar.getInstance(timeZone).getTime())
                .format(Date.from(dateTime.toInstant()));
    }
}
