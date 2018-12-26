package com.amazon.jonnu.csgotracker.service.jsoup;

import javax.annotation.Nullable;

import org.jsoup.nodes.Document;

public interface DocumentFactory {

    @Nullable
    Document getDocument(final String url);
}
