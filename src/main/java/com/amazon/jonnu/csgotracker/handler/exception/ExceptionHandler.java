package com.amazon.jonnu.csgotracker.handler.exception;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.exception.AskSdkException;
import com.amazon.ask.model.Response;

@Slf4j
public class ExceptionHandler implements com.amazon.ask.dispatcher.exception.ExceptionHandler {

    @Override
    public boolean canHandle(final HandlerInput handlerInput, final Throwable throwable) {
        return throwable instanceof AskSdkException;
    }

    @Override
    public Optional<Response> handle(final HandlerInput handlerInput, final Throwable throwable) {

        log.error("Exception was caught. Vending std error message.", throwable);

        return handlerInput.getResponseBuilder()
                .withSpeech("JONTE messed up. Try again later")
                .withSimpleCard(":(", "Something went wrong.")
                .build();
    }
}
