package com.amazon.jonnu.csgotracker.service.alexa;

import java.util.Optional;
import java.util.TimeZone;

import lombok.NonNull;

import com.amazon.jonnu.csgotracker.service.alexa.model.SettingsRequest;

public interface AlexaSettings {
    Optional<TimeZone> getTimeZone(@NonNull final SettingsRequest request);
}
