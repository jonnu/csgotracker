package com.amazon.jonnu.csgotracker.view.roster;

import com.amazon.ask.response.ResponseBuilder;
import com.amazon.jonnu.csgotracker.model.TeamRoster;
import com.amazon.jonnu.csgotracker.view.CardRenderer;
import lombok.NonNull;

public class RosterCardRenderer implements CardRenderer<TeamRoster> {

    @Override
    public void render(@NonNull final ResponseBuilder builder, @NonNull final TeamRoster toRender) {
        builder.withSimpleCard("Blah", "Blah");
    }
}
