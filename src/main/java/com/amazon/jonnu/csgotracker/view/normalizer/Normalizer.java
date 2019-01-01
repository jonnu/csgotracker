package com.amazon.jonnu.csgotracker.view.normalizer;

@FunctionalInterface
public interface Normalizer {
    String normalize(final String input);
}
