package com.amazon.jonnu.csgotracker.storage;

import com.amazon.jonnu.csgotracker.model.TeamRequest;

public interface ResourceMapper<T> {

    /**
     * Obtain a service-specific resource for the given team.
     * @param request A team request.
     * @return A team resource.
     */
    T getResource(final TeamRequest request);
}
