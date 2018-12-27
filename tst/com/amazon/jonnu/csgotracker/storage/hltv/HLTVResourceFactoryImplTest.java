package com.amazon.jonnu.csgotracker.storage.hltv;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amazon.jonnu.csgotracker.storage.ResourceRequest;
import com.amazon.jonnu.csgotracker.storage.ResourceType;
import com.amazon.jonnu.csgotracker.storage.hltv.team.TeamIdentifierStorage;

@ExtendWith(MockitoExtension.class)
class HLTVResourceFactoryImplTest {

    private static final String TEAM_NAME = "Unit Test Team";
    private static final Integer TEAM_IDENTIFIER = 666;

    @Mock
    private TeamIdentifierStorage<Integer> mockIdentifierStorage;

    private HLTVResourceFactoryImpl fixture;

    @BeforeEach
    void before() {
        fixture = new HLTVResourceFactoryImpl(mockIdentifierStorage);
    }

    @Test
    void getResourceSuccessfully() {

        when(mockIdentifierStorage.getIdentifier(TEAM_NAME)).thenReturn(TEAM_IDENTIFIER);

        ResourceRequest request = ResourceRequest.builder()
                .type(ResourceType.ROSTER)
                .identifier(TEAM_NAME)
                .build();

        HLTVResource response = fixture.getResource(request);

        assertThat(response, notNullValue());
        assertThat(response.getURL().toString(), equalTo("https://www.hltv.org/team/666/unit-test-team"));
    }

    @Test
    void getResourceWithUnresolvableTeam() {

        when(mockIdentifierStorage.getIdentifier("CannotResolve")).thenReturn(null);

        ResourceRequest request = ResourceRequest.builder()
                .type(ResourceType.ROSTER)
                .identifier("CannotResolve")
                .build();

        assertThrows(HLTVResourceException.class, () -> fixture.getResource(request));
    }
}
