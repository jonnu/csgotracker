package com.amazon.jonnu.csgotracker.view;

import com.amazon.ask.model.Response;
import com.amazon.ask.response.ResponseBuilder;
import com.amazon.jonnu.csgotracker.model.TeamRoster;
import lombok.NonNull;

import java.util.Optional;

public class TeamRosterRenderer implements Renderer<TeamRoster> {

    @Override
    public Optional<Response> render(@NonNull final TeamRoster data, @NonNull final ResponseBuilder builder) {
        return Optional.empty();
    }
}
