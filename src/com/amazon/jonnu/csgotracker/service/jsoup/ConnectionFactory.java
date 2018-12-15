package com.amazon.jonnu.csgotracker.service.jsoup;

import org.jsoup.Connection;

public interface ConnectionFactory {
    Connection getConnection(final String url);
}
