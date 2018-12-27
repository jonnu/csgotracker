package com.amazon.jonnu.csgotracker.storage.hltv;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;

import com.amazon.jonnu.csgotracker.storage.hltv.team.TeamIdentifierStorage;

@Slf4j
public class HLTVResourceFactoryImpl implements HLTVResourceFactory {

    private final TeamIdentifierStorage<Integer> identifierStorage;

    private static final String SCHEME = "https";
    private static final String DOMAIN = "www.hltv.org";

    private static final Map<ResourceType, URIBuilder> BUILDERS = ImmutableMap.<ResourceType, URIBuilder>builder()
            .put(ResourceType.ROSTER, buildURI("/team/${identifier}/${teamname}"))
            .put(ResourceType.SCHEDULE, buildURI("/matches", singleEntryMap("team", "${identifier}")))
            .put(ResourceType.RESULTS, buildURI("/results", singleEntryMap("team", "${identifier}")))
            .put(ResourceType.RANKING, buildURI("/ranking/teams"))
            .build();

    @Inject
    public HLTVResourceFactoryImpl(@NonNull final TeamIdentifierStorage<Integer> identifierStorage) {
        this.identifierStorage = identifierStorage;
    }

    public HLTVResource getResource(final ResourceRequest request) {

        Integer hltvIdentifier = Optional.ofNullable(identifierStorage.getIdentifier(request.getIdentifier()))
                .orElseThrow(() -> new RuntimeException("Unable."));

        Map<String, String> replacements = ImmutableMap.<String, String>builder()
                .put("identifier", hltvIdentifier.toString())
                // @TODO - need a team name 'normalizer' here somewhere, or 'view' onto a resource. [jonnu@]
                .put("teamname", request.getIdentifier().toLowerCase().replace(" ", "-"))
                .build();

        log.info("Using replacement map: {}", replacements);

        URL resourceURL = Optional.ofNullable(getURL(request.getType(), replacements))
                .orElseThrow(() -> new RuntimeException("Unable."));

        return HLTVResource.builder()
                .url(resourceURL)
                .build();
    }

    private static Map<String, String> singleEntryMap(final String key, final String value) {
        return ImmutableMap.<String, String>builder()
                .put(key, value)
                .build();
    }

    private static URIBuilder buildURI(@NonNull final String path) {
        return buildURI(path, Collections.emptyMap());
    }

    private static URIBuilder buildURI(@NonNull final String path, @NonNull final Map<String, String> parameters) {

        URIBuilder builder = new URIBuilder()
                .setHost(DOMAIN)
                .setScheme(SCHEME)
                .setPath(path);

        if (!parameters.isEmpty()) {
            parameters.forEach(builder::addParameter);
        }

        return builder;
    }

    private static URL getURL(final ResourceType type, final Map<String, String> replacements) {

        URIBuilder builder = BUILDERS.get(type);

        if (!StringUtils.isBlank(builder.getPath())) {
            builder.setPath(performStringReplacements(builder.getPath(), replacements));
        }

        if (!builder.getQueryParams().isEmpty()) {
            builder.getQueryParams()
                    .replaceAll(nvp -> new BasicNameValuePair(nvp.getName(),
                            performStringReplacements(nvp.getValue(), replacements)));
        }

        try {
            return builder.build().toURL();
        }
        catch (URISyntaxException | MalformedURLException exception) {
            log.error("Unable to build URL", exception);
        }

        return null;
    }

    private static String performStringReplacements(final String string, final Map<String, String> replacements) {

        if (replacements == null) {
            return string;
        }

        Pattern pattern = Pattern.compile("\\$\\{([a-z]+)}");
        Matcher matcher = pattern.matcher(string);

        StringBuilder mutable = new StringBuilder(string);

        // @TODO - this shouldnt live here. extract it. [jonnu@]
        @Value
        @RequiredArgsConstructor
        class ReplacementResult {
            private int start;
            private int end;
            private String replacement;
        }

        LinkedList<ReplacementResult> results = new LinkedList<>();

        while (matcher.find()) {

            final String replacement = replacements.getOrDefault(matcher.group(1), "");
            if (StringUtils.isBlank(replacement)) {
                log.error("Unmatched sequence in input string: {}", matcher.group(1));
            }

            results.addFirst(new ReplacementResult(matcher.start(), matcher.end(), replacement));
        }

        results.forEach(replacement -> mutable.replace(replacement.getStart(), replacement.getEnd(), replacement.getReplacement()));
        return mutable.toString();
    }

}
