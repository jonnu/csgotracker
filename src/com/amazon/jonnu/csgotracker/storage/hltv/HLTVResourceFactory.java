package com.amazon.jonnu.csgotracker.storage.hltv;

import com.amazon.jonnu.csgotracker.storage.ResourceRequest;

public interface HLTVResourceFactory {
    HLTVResource getResource(ResourceRequest request);
}
