package com.amazon.jonnu.csgotracker;

import static com.amazon.ask.utility.RequestUtility.convertToInputStream;
import static com.amazon.ask.utility.RequestUtility.convertToResponseEnvelope;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Request;
import com.amazon.ask.model.ResponseEnvelope;
import com.amazon.ask.model.Slot;

class CSGOTrackerTest {

    private CSGOTracker fixture;

    @BeforeEach
    void before() {
        fixture = new CSGOTracker();
    }

    @Test
    void functionalTeamRoster() throws Exception {
        ResponseEnvelope response = callAlexaSkill(fixture, teamRosterRequest("astralis"));
        //assertThat(response, containsOutputSpeech("this is first text"));
        System.out.println(response);
    }

    @Test
    void functionalTeamSchedule() throws Exception {
        ResponseEnvelope response = callAlexaSkill(fixture, teamScheduleRequest("astralis"));
        System.out.println(response);
    }

    private ResponseEnvelope callAlexaSkill(final SkillStreamHandler skillStreamHandler, final Request skillRequest) throws IOException {

        final InputStream inputStream = convertToInputStream(skillRequest);
        final OutputStream outputStream = new ByteArrayOutputStream();

        skillStreamHandler.handleRequest(inputStream, outputStream, null);

        return convertToResponseEnvelope(outputStream);
    }

    public static LaunchRequest launchRequest() {
        return LaunchRequest.builder()
                .withLocale("en-US")
                .withRequestId(UUID.randomUUID().toString())
                .withTimestamp(OffsetDateTime.now())
                .build();
    }

    public static IntentRequest intentRequest(final String intentName) {
        return intentRequest(intentName, Collections.emptyMap());
    }

    public static IntentRequest intentRequest(final String intentName, Map<String, Slot> intentSlots) {
        return IntentRequest.builder()
                .withLocale("en-US")
                .withTimestamp(OffsetDateTime.now())
                .withIntent(Intent.builder()
                        .withName(intentName)
                        .withSlots(intentSlots)
                        .build())
                .withRequestId(UUID.randomUUID().toString())
                .build();
    }

    public static IntentRequest teamRosterRequest(final String teamName) {
        return intentRequest("TeamRosterIntent", ImmutableMap.<String, Slot>builder()
                .put(simpleSlotEntry("TeamIdentifier", teamName))
                .build());
    }

    public static IntentRequest teamScheduleRequest(final String teamName) {
        return intentRequest("TeamScheduleIntent", ImmutableMap.<String, Slot>builder()
                .put(simpleSlotEntry("TeamIdentifier", teamName))
                .build());
    }

    public static Slot simpleSlot(final String name, final String value) {
        return Slot.builder().withName(name).withValue(value).build();
    }

    public static Map.Entry<String, Slot> simpleSlotEntry(final String name, final String value) {
        return new java.util.AbstractMap.SimpleEntry<>(name, simpleSlot(name, value));
    }
}
