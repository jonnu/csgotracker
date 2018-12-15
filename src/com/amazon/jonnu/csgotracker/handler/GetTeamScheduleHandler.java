package com.amazon.jonnu.csgotracker.handler;

import static com.amazon.ask.request.Predicates.intentName;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;

import com.google.inject.Inject;
import lombok.NonNull;
import org.ocpsoft.prettytime.PrettyTime;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazon.jonnu.csgotracker.model.TeamScheduleResult;
import com.amazon.jonnu.csgotracker.service.CrappyScheduleInterface;
import com.amazon.jonnu.csgotracker.service.EntityResolver;
import com.amazon.jonnu.csgotracker.service.alexa.AlexaSettings;
import com.amazon.jonnu.csgotracker.service.alexa.model.SettingsRequest;

public class GetTeamScheduleHandler implements RequestHandler {

    private static final String SLOT_TEAM_IDENTIFIER = "TeamIdentifier";

    private final EntityResolver resolver;
    private final CrappyScheduleInterface storage;
    private final AlexaSettings alexaSettings;

    private static final PrettyTime prettyTime = new PrettyTime();

    @Inject
    public GetTeamScheduleHandler(@NonNull final EntityResolver resolver, @NonNull final CrappyScheduleInterface storage, @NonNull final AlexaSettings alexaSettings) {
        this.resolver = resolver;
        this.storage = storage;
        this.alexaSettings = alexaSettings;
    }

    @Override
    public boolean canHandle(final HandlerInput input) {
        return input.matches(intentName("TeamScheduleIntent"));
    }

    @Override
    public Optional<Response> handle(final HandlerInput input) {

        TimeZone timeZone = alexaSettings.getTimeZone(SettingsRequest.builder()
                .apiToken(input.getRequestEnvelope().getContext().getSystem().getApiAccessToken())
                .endpoint(input.getRequestEnvelope().getContext().getSystem().getApiEndpoint())
                .deviceId(input.getRequestEnvelope().getContext().getSystem().getDevice().getDeviceId())
                .build())
                .orElse(TimeZone.getDefault());

        final Optional<Slot> teamIdentifier = resolver.getSlotByName(input, SLOT_TEAM_IDENTIFIER);

        if (!teamIdentifier.isPresent()) {
            return Optional.empty();
        }

        TeamScheduleResult result = storage.getUpcomingMatches().get(0);

        final String speechString = String.format("%s will play %s at %s on %s",
                result.getQueriedTeam().getSpokenIdentifier(),
                result.getOpponentTeam().getSpokenIdentifier(),
                getPrettyTime(result.getDateTime(), timeZone),
                result.getMap().getSpokenIdentifier()
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
