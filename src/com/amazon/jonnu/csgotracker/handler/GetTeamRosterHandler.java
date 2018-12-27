package com.amazon.jonnu.csgotracker.handler;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.Locale;
import java.util.Optional;

import com.google.inject.Inject;
import lombok.NonNull;
import org.apache.commons.lang3.LocaleUtils;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.jonnu.csgotracker.model.Renderable;
import com.amazon.jonnu.csgotracker.model.TeamRequest;
import com.amazon.jonnu.csgotracker.model.TeamRoster;
import com.amazon.jonnu.csgotracker.storage.TeamDataRetriever;
import com.amazon.jonnu.csgotracker.view.Renderer;

public class GetTeamRosterHandler implements RequestHandler {

    private final TeamDataRetriever retriever;
    private final Renderer<Renderable> renderer;

    @Inject
    public GetTeamRosterHandler(@NonNull final TeamDataRetriever retriever, @NonNull final Renderer<Renderable> renderer) {
        this.retriever = retriever;
        this.renderer = renderer;
    }

    @Override
    public boolean canHandle(final HandlerInput input) {
        return input.matches(intentName("TeamRosterIntent"));
    }

    @Override
    public Optional<Response> handle(final HandlerInput input) {

        // @TODO try/catch the handle into a generic error handler.
        TeamRequest request = create((IntentRequest) input.getRequestEnvelope().getRequest());

        TeamRoster roster = retriever.getTeamRoster(request);

        return renderer.render(roster);
    }

    private TeamRequest create(final IntentRequest request) {
        return TeamRequest.builder()
                .locale(parseLocale(request.getLocale()))
                .teamName(request.getIntent().getSlots().get("TeamIdentifier").getValue())
                .build();
    }

    private Locale parseLocale(final String locale) {
        return locale.contains("-") ? Locale.forLanguageTag(locale) : LocaleUtils.toLocale(locale);
    }
}
