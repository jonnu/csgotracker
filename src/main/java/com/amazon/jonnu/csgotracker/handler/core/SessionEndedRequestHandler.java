package com.amazon.jonnu.csgotracker.handler.core;

import static com.amazon.ask.request.Predicates.requestType;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Request;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Session;
import com.amazon.ask.model.SessionEndedRequest;

@Slf4j
public class SessionEndedRequestHandler implements RequestHandler {

    @Override
    public boolean canHandle(final HandlerInput input) {
        return input.matches(requestType(SessionEndedRequest.class));
    }

    @Override
    public Optional<Response> handle(final HandlerInput input) {

        final Request request = input.getRequestEnvelope().getRequest();
        final Session session = input.getRequestEnvelope().getSession();

        log.info("onSessionEnded requestId: {}, sessionId: {}", request.getRequestId(), session.getSessionId());

        return input.getResponseBuilder().build();
    }
}
