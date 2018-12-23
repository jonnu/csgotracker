package com.amazon.jonnu.csgotracker.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.jonnu.csgotracker.model.Renderable;
import com.amazon.jonnu.csgotracker.model.TeamRequest;
import com.amazon.jonnu.csgotracker.model.TeamRoster;
import com.amazon.jonnu.csgotracker.storage.TeamDataRetriever;
import com.amazon.jonnu.csgotracker.view.Renderer;
import com.google.inject.Inject;
import lombok.NonNull;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class GetTeamRosterHandler implements RequestHandler {

    private final TeamDataRetriever dataRetriever;
    private final Renderer<Renderable> dataRenderer;

    @Inject
    public GetTeamRosterHandler(@NonNull final TeamDataRetriever dataRetriever, @NonNull final Renderer<Renderable> dataRenderer) {
        this.dataRetriever = dataRetriever;
        this.dataRenderer = dataRenderer;
    }

    @Override
    public boolean canHandle(final HandlerInput input) {
        return input.matches(intentName("TeamRosterIntent"));
    }

    @Override
    public Optional<Response> handle(final HandlerInput input) {

        TeamRequest request = TeamRequest.builder()
                .teamName("Astralis")
                .build();

        TeamRoster roster = dataRetriever.getTeamRoster(request);

        return dataRenderer.render(roster);
    }

}
