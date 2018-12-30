package com.amazon.jonnu.csgotracker.storage;

import com.amazon.jonnu.csgotracker.model.TeamNews;
import com.amazon.jonnu.csgotracker.model.TeamRequest;
import com.amazon.jonnu.csgotracker.model.TeamResults;
import com.amazon.jonnu.csgotracker.model.TeamRoster;
import com.amazon.jonnu.csgotracker.model.TeamSchedule;

import javax.annotation.Nullable;

public interface TeamDataRetriever {

    /**
     * Fetch news about the requested team.
     * @param request A team request.
     * @return A {@link TeamNews} model.
     */
    @Nullable
    TeamNews getTeamNews(final TeamRequest request);

    /**
     * Fetch the current roster of the requested team.
     * @param request A team request.
     * @return A {@link TeamNews} model.
     */
    @Nullable
    TeamRoster getTeamRoster(final TeamRequest request);

    /**
     * Fetch the upcoming schedule for the requested team.
     * @param request A team request.
     * @return A {@link TeamNews} model.
     */
    @Nullable
    TeamSchedule getTeamSchedule(final TeamRequest request);

    /**
     * Fetch the recent results for the requested team.
     * @param request  A team request.
     * @return A {@link TeamNews} model.
     */
    @Nullable
    TeamResults getTeamResults(final TeamRequest request);
}
