package com.amazon.jonnu.csgotracker.handler;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Locale;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import com.amazon.ask.response.ResponseBuilder;
import com.amazon.jonnu.csgotracker.handler.csgo.GetTeamRosterHandler;
import com.amazon.jonnu.csgotracker.model.TeamRequest;
import com.amazon.jonnu.csgotracker.model.TeamRoster;
import com.amazon.jonnu.csgotracker.storage.TeamDataRetriever;
import com.amazon.jonnu.csgotracker.view.renderer.Renderer;

@ExtendWith(MockitoExtension.class)
class GetTeamRosterHandlerTest extends AbstractHandlerTest {

    private static final String INTENT_VALID = "TeamRosterIntent";
    private static final String INTENT_INVALID = "TeamScheduleIntent";

    @Mock private TeamDataRetriever mockDataRetriever;
    @Mock private Renderer<TeamRoster> mockDataRenderer;
    @Captor private ArgumentCaptor<TeamRequest> teamRequestCaptor;

    private GetTeamRosterHandler fixture;

    @BeforeEach
    void before() {
        fixture = new GetTeamRosterHandler(mockDataRetriever, mockDataRenderer);
    }

    @Test
    void canHandleWithValidIntent() {
        boolean canHandle = fixture.canHandle(getIntentRequestHandlerInput(INTENT_VALID));
        assertThat("TeamRosterIntent should be handled.", canHandle, is(true));
    }

    @Test
    void canHandleWithInvalidIntent() {
        boolean canHandle = fixture.canHandle(getIntentRequestHandlerInput(INTENT_INVALID));
        assertThat("TeamScheduleIntent should not be handled.", canHandle, is(false));
    }

    @Test
    void handleRequest() {

        final String requestedTeamName = "Astralis";
        final HandlerInput handlerInput = getIntentRequestHandlerInput(INTENT_VALID, getTeamIdentifierSlotMap(requestedTeamName));

        final TeamRoster mockedTeamRoster = TeamRoster.builder()
                .teamName(requestedTeamName)
                .individuals(Collections.emptyList())
                .build();

        when(mockDataRetriever.getTeamRoster(any(TeamRequest.class))).thenReturn(mockedTeamRoster);
        when(mockDataRenderer.render(handlerInput, mockedTeamRoster)).thenReturn(new ResponseBuilder().withSpeech("Unit Test").build());

        Optional<Response> response = fixture.handle(handlerInput);

        assertThat("A response from the handler is expected.", response.isPresent(), is(true));
        assertThat(response.get().getOutputSpeech().toString(), containsString("Unit Test"));

        verify(mockDataRetriever).getTeamRoster(teamRequestCaptor.capture());
        verify(mockDataRenderer).render(handlerInput, mockedTeamRoster);

        assertThat(teamRequestCaptor.getValue().getTeamName(), equalTo(requestedTeamName));
        assertThat(teamRequestCaptor.getValue().getLocale(), equalTo(Locale.US));
    }
}
