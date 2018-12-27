package com.amazon.jonnu.csgotracker.storage.hltv;

import java.util.Map;
import javax.annotation.Nullable;

import com.google.inject.Inject;
import lombok.NonNull;

import com.amazon.jonnu.csgotracker.model.Renderable;
import com.amazon.jonnu.csgotracker.storage.hltv.parser.HLTVDocumentParser;

public class HLTVDocumentParserFactoryImpl implements HLTVDocumentParserFactory {

    private final Map<Class<? extends Renderable>, HLTVDocumentParser<? extends Renderable>> parsers;

    @Inject
    public HLTVDocumentParserFactoryImpl(final Map<Class<? extends Renderable>, HLTVDocumentParser<? extends Renderable>> parsers) {
        this.parsers = parsers;
    }

    @Override
    @Nullable
    @SuppressWarnings("unchecked")
    public <T extends Renderable> HLTVDocumentParser<T> getParser(@NonNull final Class<T> modelClass) {
        return (HLTVDocumentParser<T>) parsers.get(modelClass);
    }
}
