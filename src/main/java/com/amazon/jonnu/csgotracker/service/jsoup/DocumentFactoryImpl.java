package com.amazon.jonnu.csgotracker.service.jsoup;

import java.io.IOException;
import javax.annotation.Nullable;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

@Slf4j
public class DocumentFactoryImpl implements DocumentFactory {

    private static String USER_AGENT = "github.com/jonnu/csgotracker; Alexa Skill; jonnu <jon@ellis-jones.org>";
    private static Connection connection;

    @Nullable
    @Override
    public Document getDocument(final String url) {
        try {
            log.info("Fetching HTML document: {}", url);
            return getConnection(url)
                    .method(Connection.Method.GET)
                    .parser(Parser.htmlParser())
                    .get();
        }
        catch (IOException exception) {
            log.error("Unable to fetch HTML document.", exception);
            return null;
        }
    }

    private Connection getConnection(final String url) {
        return Jsoup.connect(url)
                .userAgent(USER_AGENT)
                .followRedirects(true);
    }
}
