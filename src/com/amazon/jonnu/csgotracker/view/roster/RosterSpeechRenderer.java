package com.amazon.jonnu.csgotracker.view.roster;

import com.amazon.ask.response.ResponseBuilder;
import com.amazon.jonnu.csgotracker.model.TeamRoster;
import com.amazon.jonnu.csgotracker.view.OutputSpeechRenderer;
import lombok.NonNull;

public class RosterSpeechRenderer implements OutputSpeechRenderer<TeamRoster> {

    @Override
    public void render(@NonNull final ResponseBuilder builder, @NonNull final TeamRoster data) {
        builder.withSpeech("Hello");
    }
}
