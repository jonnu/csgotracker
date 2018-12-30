package com.amazon.jonnu.csgotracker.storage.hltv;

import com.amazon.jonnu.csgotracker.model.Renderable;

public interface HLTVDataDelegate {
    <T extends Renderable> T getRenderableModel(Class<T> model, HLTVResource resource);
}
