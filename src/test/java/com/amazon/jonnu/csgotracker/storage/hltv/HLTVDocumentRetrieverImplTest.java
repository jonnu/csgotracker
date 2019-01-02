package com.amazon.jonnu.csgotracker.storage.hltv;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amazon.jonnu.csgotracker.service.jsoup.DocumentFactory;

@ExtendWith(MockitoExtension.class)
class HLTVDocumentRetrieverImplTest {

    private HLTVDocumentRetriever fixture;

    @Mock private DocumentFactory mockDocumentFactory;

    @BeforeEach
    void before() {
        fixture = new HLTVDocumentRetrieverImpl(mockDocumentFactory);
    }

    @Test
    void nullResourceThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> fixture.getDocument(null));
    }

    @Test
    void resourceUrlPassedCorrectly() {
        URL dummyURLResource = createURL("http://www.hltv.org/dummy/666");

        fixture.getDocument(HLTVResource.builder().url(dummyURLResource).build());

        verify(mockDocumentFactory).getDocument(eq(dummyURLResource));
    }

    private URL createURL(final String url) {
        try {
            return new URL(url);
        }
        catch (MalformedURLException exception) {
            throw new RuntimeException("Unable to create URL object.", exception);
        }
    }
}
