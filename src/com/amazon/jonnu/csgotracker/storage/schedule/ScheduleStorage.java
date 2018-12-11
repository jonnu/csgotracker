package com.amazon.jonnu.csgotracker.storage.schedule;

import com.amazon.jonnu.csgotracker.model.Team;
import com.amazon.jonnu.csgotracker.model.TeamScheduleResult;

public interface ScheduleStorage {
    TeamScheduleResult getNextSchedule(final Team team);
}
