package com.amazon.jonnu.csgotracker.storage.hltv;

public interface HLTVResourceFactory {
    HLTVResource getResource(ResourceRequest request);
}
