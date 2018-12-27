package com.amazon.jonnu.csgotracker.storage.hltv;

public class HLTVResourceException extends RuntimeException {

    HLTVResourceException(final String message) {
        super(message);
    }

    HLTVResourceException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
