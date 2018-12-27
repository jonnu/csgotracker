package com.amazon.jonnu.csgotracker.storage.hltv.team;

import javax.annotation.Nullable;

@FunctionalInterface
public interface TeamIdentifierStorage<T> {
    @Nullable
    T getIdentifier(final String name);
}
