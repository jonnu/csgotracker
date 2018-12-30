package com.amazon.jonnu.csgotracker.storage.hltv;

import org.jsoup.nodes.Document;

public interface HLTVDocumentRetriever {
    Document getDocument(final HLTVResource resource);
}
