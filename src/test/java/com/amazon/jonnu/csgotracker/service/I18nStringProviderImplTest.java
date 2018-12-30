package com.amazon.jonnu.csgotracker.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class I18nStringProviderImplTest {

    private I18nStringProvider fixture;

    @BeforeEach
    void before() {
        fixture = new I18nStringProviderImpl(Locale.US);
    }

    @Test
    void keyExistsLocaleExists() {
        String actual = fixture.getString("roster.team");
        assertThat(actual, notNullValue());
    }

//    @Test
//    void keyExistsLocaleUnknown() {
//        String actual = fixture.getString("roster.team", Locale.KOREA);
//        System.out.println(actual);
//        assertThat(actual, notNullValue());
//    }

    @Test
    void keyExistsWithParameters() {
        String actual = fixture.getString("error.unknown_team", Locale.US, "Test");
        System.out.println(actual);
    }
}
