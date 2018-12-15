package com.amazon.jonnu.csgotracker.model;

import java.time.ZonedDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TeamScheduleResult {
    private final Team queriedTeam;
    private final Team opponentTeam;
    private final ZonedDateTime dateTime;
    // TODO: encapsulate as MapList model.
    private final List<Map> map;
}
