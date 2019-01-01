package com.amazon.jonnu.csgotracker.view.renderer;

import java.util.Optional;

import lombok.NonNull;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import com.amazon.ask.response.ResponseBuilder;
import com.amazon.jonnu.csgotracker.model.TeamSchedule;

public class TeamScheduleRenderer implements Renderer<TeamSchedule> {

    @Override
    public Optional<Response> render(@NonNull final HandlerInput input, @NonNull final TeamSchedule data, @NonNull final ResponseBuilder builder) {
        return builder.build();
    }
}
