package com.amazon.jonnu.csgotracker.storage.hltv;

import javax.annotation.Nullable;

import com.amazon.jonnu.csgotracker.model.Renderable;
import com.amazon.jonnu.csgotracker.storage.hltv.parser.HLTVDocumentParser;

@FunctionalInterface
public interface HLTVDocumentParserFactory {
    @Nullable <T extends Renderable> HLTVDocumentParser<T> getParser(Class<T> clazz);
}
