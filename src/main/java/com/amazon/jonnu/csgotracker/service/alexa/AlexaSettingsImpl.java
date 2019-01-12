package com.amazon.jonnu.csgotracker.service.alexa;

import java.util.Optional;
import java.util.TimeZone;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import com.amazon.ask.model.services.ServiceException;
import com.amazon.ask.model.services.ups.UpsService;
import com.amazon.jonnu.csgotracker.service.alexa.model.SettingsRequest;

@Slf4j
public class AlexaSettingsImpl implements AlexaSettings {

    private static final String PATH_TEMPLATE_TIMEZONE = "/v2/devices/{deviceId}/settings/System.timeZone";

    @Inject
    public AlexaSettingsImpl() {
        //
    }

    public Optional<TimeZone> getTimeZone(@NonNull final UpsService service, @NonNull final String deviceId) {
        try {
            return Optional.of(service.getSystemTimeZone(deviceId))
                    .map(TimeZone::getTimeZone);
        }
        catch (ServiceException exception) {
            log.warn("Unable to obtain time zone for device {}", deviceId, exception);
            return Optional.empty();
        }
    }

    @Override
    public Optional<TimeZone> getTimeZone(@NonNull final SettingsRequest request) {

        Client client = ClientBuilder.newClient();

        WebTarget webTarget = client.target(request.getEndpoint())
                .path(PATH_TEMPLATE_TIMEZONE)
                .resolveTemplate("deviceId", request.getDeviceId());

        Invocation invocation = webTarget.request(MediaType.APPLICATION_JSON_TYPE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + request.getApiToken())
                .buildGet();

        Response response = invocation.invoke();

        if (response.getStatus() != 200) {
            log.error("fail, got {}", response.getStatus(), response.getHeaders());
            return Optional.empty();
        }

        return Optional.of(TimeZone.getTimeZone(response.readEntity(String.class)));
    }

}
