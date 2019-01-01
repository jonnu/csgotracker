package com.amazon.ask.assertion;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import lombok.AllArgsConstructor;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import com.amazon.ask.model.ResponseEnvelope;
import com.amazon.ask.model.ui.SsmlOutputSpeech;

@AllArgsConstructor
public class OutputSpeechMatcher extends TypeSafeMatcher<ResponseEnvelope> {

    private static final String TAG_OPEN = "<speak>";
    private static final String TAG_CLOSE = "</speak>";

    private final Matcher<String> speechMatcher;

    public static Matcher<ResponseEnvelope> containsOutputSpeech(final String speech) {
        return new OutputSpeechMatcher(containsString(speech));
    }

    public static Matcher<ResponseEnvelope> hasOutputSpeech(final String speech) {
        return new OutputSpeechMatcher(equalTo(speech));
    }

    @Override
    protected boolean matchesSafely(final ResponseEnvelope responseEnvelope) {

        SsmlOutputSpeech outputSpeech = (SsmlOutputSpeech) responseEnvelope.getResponse().getOutputSpeech();
        String ssml = stripSsmlTags(outputSpeech.getSsml());

        return speechMatcher.matches(ssml);
    }

    private static String stripSsmlTags(final String ssml) {

        if (!ssml.startsWith(TAG_OPEN) || !ssml.endsWith(TAG_CLOSE)) {
            return ssml;
        }

        return ssml.substring(TAG_OPEN.length(), ssml.length() - TAG_CLOSE.length());
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("a response with output speech ").appendDescriptionOf(speechMatcher);
    }
}
