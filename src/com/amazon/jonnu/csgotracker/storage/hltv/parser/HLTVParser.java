package com.amazon.jonnu.csgotracker.storage.hltv.parser;

import org.jsoup.nodes.Document;

public interface HLTVParser<T> {
    T parse(final Document document);
}
