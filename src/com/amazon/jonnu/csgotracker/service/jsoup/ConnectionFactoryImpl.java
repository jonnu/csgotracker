package com.amazon.jonnu.csgotracker.service.jsoup;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class ConnectionFactoryImpl implements ConnectionFactory {

    private static String USER_AGENT = "github.com/jonnu/csgotracker; Alexa Skill; jonnu <jon@ellis-jones.org>";

    @Override
    public Connection getConnection(final String url) {
        return Jsoup.connect(url)
                .userAgent(USER_AGENT)
                .followRedirects(true);
    }
}
