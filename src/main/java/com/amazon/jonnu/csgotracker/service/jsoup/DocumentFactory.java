package com.amazon.jonnu.csgotracker.service.jsoup;

import java.net.URL;
import javax.annotation.Nullable;

import org.jsoup.nodes.Document;

public interface DocumentFactory {

    @Nullable
    Document getDocument(final String url);

    default Document getDocument(final URL url) {
        return getDocument(url.toString());
    }
}
