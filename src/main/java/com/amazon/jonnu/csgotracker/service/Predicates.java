package com.amazon.jonnu.csgotracker.service;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

import lombok.experimental.UtilityClass;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;

@UtilityClass
public class Predicates {

    private static <T> Predicate<T> andChain(final Predicate<T>... predicates) {
        return andChain(Arrays.stream(predicates));
    }

    private static <T> Predicate<T> andChain(final Stream<Predicate<T>> predicates) {
        return predicates.reduce(p -> true, Predicate::and);
    }

    private static <T> Predicate<T> orChain(final Predicate<T>... predicates) {
        return orChain(Arrays.stream(predicates));
    }

    private static <T> Predicate<T> orChain(final Stream<Predicate<T>> predicates) {
        return predicates.reduce(p -> false, Predicate::or);
    }

    public static Predicate<HandlerInput> intentNameOneOf(final String... intentNames) {
        return orChain(Arrays.stream(intentNames).map(com.amazon.ask.request.Predicates::intentName));
    }
}
