package com.amazon.ask.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import com.amazon.ask.model.Application;
import com.amazon.ask.model.Device;
import com.amazon.ask.model.Request;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.ResponseEnvelope;
import com.amazon.ask.model.Session;
import com.amazon.ask.model.User;
import com.amazon.ask.model.interfaces.system.SystemState;
import com.amazon.ask.util.impl.ObjectMapperFactory;

@UtilityClass
public class RequestUtility {

    private final static ObjectMapper OBJECT_MAPPER = ObjectMapperFactory.getMapper();

    public static InputStream convertToInputStream(final Request request) throws IOException {

        String skillId = "amzn1.ask.skill.f0328fa9-5677-4701-9738-b8608df39bac";

        final RequestEnvelope envelope = RequestEnvelope.builder()
                .withRequest(request)
                .withContext(com.amazon.ask.model.Context.builder()
                        .withSystem(SystemState.builder()
                                .withApplication(getApplication(skillId))
                                .withApiAccessToken("unitTestApiAccessToken")
                                .withApiEndpoint("https://api.eu.amazonalexa.com/")
                                .withDevice(Device.builder()
                                        .withDeviceId(UUID.randomUUID().toString())
                                        .build())
                                .build())
                        .build())
                .withSession(Session.builder()
                        .withApplication(getApplication(skillId))
                        .withSessionId(UUID.randomUUID().toString())
                        .withUser(User.builder()
                                .withUserId("unitTest")
                                .withAccessToken(UUID.randomUUID().toString())
                                .build())
                        .build())
                .withVersion("1.0.0.0")
                .build();

        return marshall(envelope);
    }

    public static ResponseEnvelope convertToResponseEnvelope(final OutputStream output) throws IOException {
        return unmarshall(output, ResponseEnvelope.class);
    }

    private static Application getApplication(final String applicationId) {
        return Application.builder().withApplicationId(applicationId).build();
    }

    private static InputStream marshall(@NonNull final Object data) throws IOException {
        return new ByteArrayInputStream(OBJECT_MAPPER.writeValueAsString(data).getBytes(StandardCharsets.UTF_8));
    }

    private static <T> T unmarshall(final OutputStream outputStream, Class<T> type) throws IOException {
        return OBJECT_MAPPER.readValue(((ByteArrayOutputStream) outputStream).toByteArray(), type);
    }
}
