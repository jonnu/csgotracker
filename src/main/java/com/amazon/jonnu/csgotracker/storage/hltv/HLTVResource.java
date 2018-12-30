package com.amazon.jonnu.csgotracker.storage.hltv;

import java.net.URL;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class HLTVResource {

    private final URL url;

    public URL getURL() {
        return url;
    }
}
