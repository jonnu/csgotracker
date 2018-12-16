package com.amazon.jonnu.csgotracker.storage.hltv;

import java.util.Optional;

public interface TeamIndex {
    Optional<Integer> getIdentifier(final String identifier);
}
