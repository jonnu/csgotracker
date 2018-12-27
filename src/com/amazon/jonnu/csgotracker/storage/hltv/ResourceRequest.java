package com.amazon.jonnu.csgotracker.storage.hltv;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ResourceRequest {
    private final ResourceType type;
    private final String identifier;
}
