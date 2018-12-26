package com.amazon.jonnu.csgotracker.storage.hltv.parser;

import org.jsoup.nodes.Document;

import com.amazon.jonnu.csgotracker.model.Renderable;

public interface HLTVDocumentParser<T extends Renderable> {
    T parse(final Document document);
}
