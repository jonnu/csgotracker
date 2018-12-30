package com.amazon.jonnu.csgotracker.view;

import com.amazon.ask.model.Response;
import com.amazon.ask.response.ResponseBuilder;
import com.amazon.jonnu.csgotracker.model.TeamSchedule;
import lombok.NonNull;

import java.util.Optional;

public class TeamSchduleRenderer implements Renderer<TeamSchedule> {

    @Override
    public Optional<Response> render(@NonNull final TeamSchedule data, @NonNull final ResponseBuilder builder) {
        return Optional.empty();
    }
}
