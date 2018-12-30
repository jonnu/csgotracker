package com.amazon.jonnu.csgotracker.storage.hltv;

import java.util.Optional;

import com.google.inject.Inject;
import lombok.NonNull;
import org.jsoup.nodes.Document;

import com.amazon.jonnu.csgotracker.model.Renderable;

public class HLTVDataDelegateImpl implements HLTVDataDelegate {

    private final HLTVDocumentParserFactory parserFactory;
    private final HLTVDocumentRetriever documentRetriever;

    @Inject
    public HLTVDataDelegateImpl(@NonNull final HLTVDocumentParserFactory parserFactory, @NonNull final HLTVDocumentRetriever documentRetriever) {
        this.parserFactory = parserFactory;
        this.documentRetriever = documentRetriever;
    }

    public <T extends Renderable> T getRenderableModel(@NonNull final Class<T> model, @NonNull final HLTVResource resource) {
        Document document = documentRetriever.getDocument(resource);
        return Optional.ofNullable(parserFactory.getParser(model))
                .map(parser -> parser.parse(document))
                .orElseThrow(() -> new IllegalStateException("What!"));
    }
}
