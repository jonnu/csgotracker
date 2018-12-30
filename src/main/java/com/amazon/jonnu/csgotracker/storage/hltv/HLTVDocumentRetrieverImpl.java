package com.amazon.jonnu.csgotracker.storage.hltv;

import com.google.inject.Inject;
import lombok.NonNull;
import org.jsoup.nodes.Document;

import com.amazon.jonnu.csgotracker.service.jsoup.DocumentFactory;

public class HLTVDocumentRetrieverImpl implements HLTVDocumentRetriever {

    private DocumentFactory factory;

    @Inject
    public HLTVDocumentRetrieverImpl(@NonNull final DocumentFactory factory) {
        this.factory = factory;
    }

    @Override
    public Document getDocument(@NonNull final HLTVResource resource) {
        return factory.getDocument(resource.getURL());
    }
}
