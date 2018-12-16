package com.amazon.jonnu.csgotracker.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.jonnu.csgotracker.model.TeamRequest;
import com.amazon.jonnu.csgotracker.model.TeamRoster;
import com.amazon.jonnu.csgotracker.storage.TeamDataRetriever;
import com.google.inject.Inject;
import lombok.NonNull;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class GetTeamRosterHandler implements RequestHandler {

    private final TeamDataRetriever dataRetriever;

    @Inject
    public GetTeamRosterHandler(@NonNull final TeamDataRetriever dataRetriever) {
        this.dataRetriever = dataRetriever;
    }

    @Override
    public boolean canHandle(final HandlerInput input) {
        return input.matches(intentName("TeamRosterIntent"));
    }

    @Override
    public Optional<Response> handle(final HandlerInput input) {

        TeamRequest request = TeamRequest.builder()
                .teamName("")
                .build();

        TeamRoster roster = dataRetriever.getTeamRoster(request);

        return renderFactory.getRenderChain(TeamRoster.class)
                .render(roster);
//        Parser<TeamRoster>
//
//        return Optional.empty();
    }

}
