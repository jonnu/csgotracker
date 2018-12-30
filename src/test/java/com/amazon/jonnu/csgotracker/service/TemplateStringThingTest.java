package com.amazon.jonnu.csgotracker.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TemplateStringThingTest {

    private TemplateStringThing fixture;

    @BeforeEach
    void before() {
        fixture = new TemplateStringThing();
    }

    @Test
    void replacementNormalized() {
        String template = "Template            with    ${variable}     odd spacing.";
        final String actual = fixture.replace(template, ImmutableMap.of("variable", SimpleTemplateVariable.builder().variable("some").build()));
        assertThat(actual, equalTo("Template with some odd spacing."));
    }

    @Test
    void replacementWithNoReplacements() {
        String template = "Template that ${missing} removes ${some} missing ${variable:replacements} replacements.";
        final String actual = fixture.replace(template, Collections.emptyMap());
        assertThat(actual, equalTo("Template that removes missing replacements."));
    }

    @Test
    void replacementWithSimpleAndIterable() {

        String template = "the current roster for ${team} is ${oxford:individuals}.";

        List<String> individuals = ImmutableList.<String>builder()
                .add("First", "Second", "Third", "Fourth", "Fifth")
                .build();

        Map<String, TemplateVariable> vars = ImmutableMap.<String, TemplateVariable>builder()
                .put("team", SimpleTemplateVariable.builder().key("team").variable("Test Team").build())
                .put("individuals", IterableTemplateVariable.builder().key("individuals").variable(individuals).build())
                .build();

        final String actual = fixture.replace(template, vars);

        assertThat(actual, equalTo("the current roster for Test Team is First, Second, Third, Fourth, and Fifth."));
    }
}
