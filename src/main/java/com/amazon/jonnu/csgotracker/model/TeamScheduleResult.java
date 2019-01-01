package com.amazon.jonnu.csgotracker.model;

import java.time.ZonedDateTime;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TeamScheduleResult {
    private final Team queriedTeam;
    private final Team opponentTeam;
    private final ZonedDateTime dateTime;
    private final MapList mapList;
}
