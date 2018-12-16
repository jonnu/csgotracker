package com.amazon.jonnu.csgotracker.storage;

import com.amazon.jonnu.csgotracker.model.TeamRequest;

public interface TeamIdMapper<T> {

    /**
     * Obtain a service-specific identifier for the given team.
     * @param request A team request.
     * @return A team identifier.
     */
    T getIdentifier(final TeamRequest request);
}
