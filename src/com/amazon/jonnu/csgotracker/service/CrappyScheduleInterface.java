package com.amazon.jonnu.csgotracker.service;

import java.util.List;

import com.amazon.jonnu.csgotracker.model.TeamScheduleResult;

public interface CrappyScheduleInterface {
    List<TeamScheduleResult> getUpcomingMatches(final int teamIdentifier);
}
