package com.amazon.jonnu.csgotracker.storage.hltv;

import javax.annotation.Nullable;

import com.google.inject.Inject;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import com.amazon.jonnu.csgotracker.model.TeamNews;
import com.amazon.jonnu.csgotracker.model.TeamRequest;
import com.amazon.jonnu.csgotracker.model.TeamResults;
import com.amazon.jonnu.csgotracker.model.TeamRoster;
import com.amazon.jonnu.csgotracker.model.TeamSchedule;
import com.amazon.jonnu.csgotracker.storage.TeamDataRetriever;

@Slf4j
public class HLTVTeamDataRetriever implements TeamDataRetriever {

    //private final ResourceMapper<Integer> idMapper;
    private final HLTVDataDelegate delegate;
    private final HLTVResourceFactoryImpl factory;
    //private final HLTVDocumentParserFactory documentParserFactory;

//    private final TeamIndex teamIndex;
//    private final DocumentFactory connectionFactory;
//    @Inject
//    public HLTVTeamDataRetriever(@NonNull final TeamIndex teamIndex, @NonNull final DocumentFactory connectionFactory) {
//        this.teamIndex = teamIndex;
//        this.connectionFactory = connectionFactory;
//    }

    @Inject
    public HLTVTeamDataRetriever(@NonNull final HLTVDataDelegate delegate, @NonNull final HLTVResourceFactoryImpl factory) {
        this.factory = factory;
        this.delegate = delegate;
    }

    @Nullable
    @Override
    public TeamNews getTeamNews(@NonNull final TeamRequest request) {
        return null;
    }

    @Nullable
    @Override
    public TeamRoster getTeamRoster(@NonNull final TeamRequest request) {

        log.info("TeamRoster request with {}", request);

        final ResourceRequest resourceRequest = ResourceRequest.builder()
                .identifier(request.getTeamName())
                .type(ResourceType.ROSTER)
                .build();

        HLTVResource resource = factory.getResource(resourceRequest);
        log.info("Translated request {} to resource {}", resourceRequest, resource);

        return delegate.getRenderableModel(TeamRoster.class, resource);
    }

    @Nullable
    @Override
    public TeamSchedule getTeamSchedule(@NonNull final TeamRequest request) {
        return null;
    }

    @Nullable
    @Override
    public TeamResults getTeamResults(@NonNull final TeamRequest request) {
        return null;
    }

        /*return documentParserFactory.getParser(TeamRoster.class).parse(document)
                .getParser(TeamRoster.class)
                .withUrlResource(resourceUrl)
                .parse();
                */

    //        documentParserFactory.getParser(TeamRoster.class)
    //        HLTVDocumentParser<Team> parser = new TeamRosterParser();

    //TeamRoster roster = getDocAndParse(resource, parser);
    //final Document document = connectionFactory.getConnection("https://www.hltv.org/team/6665/astralis")
    //                    .get();
    //
    //            HLTVDocumentParser<Team> parser = new TeamRosterParser();
    //            return Optional.of(parser.parse(document));


//    public Optional<Integer> getTeamIdentifier(final String teamName) {
//        return teamIndex.getResource(teamName);
//    }
//
//    public List<TeamScheduleResult> getUpcomingMatches(final int teamIdentifier) {
//
//        // fetch(TeamDataRetriever.MATCHES, id).then(parser);
//        try {
//
//            // All Astralis matches.
//            final Document document = connectionFactory.getConnection("https://www.hltv.org/matches?team=" + teamIdentifier)
//                    .get();
//
//            log.error(document.baseUri());
//
//            return document.select(".matches .upcoming-matches a").stream()
//                    //.peek(System.out::println)
//                    .map(element -> {
//                        Elements teamElements = element.select("td.team-cell .team");
//                        return TeamScheduleResult.builder()
//                                .queriedTeam(Team.builder().spokenIdentifier(teamElements.get(0).text()).displayIdentifier(teamElements.get(0).text()).roster(Collections.emptyList()).build())
//                                .opponentTeam(Team.builder().spokenIdentifier(teamElements.get(1).text()).displayIdentifier(teamElements.get(1).text()).roster(Collections.emptyList()).build())
//                                .dateTime(convertEpochToZonedDateTime(element.selectFirst("td.time > .time").attr("data-unix")))
//                                .mapList(MapList.create(element.selectFirst("td.star-cell .map-text").text()))
//                                .build();
//                    })
//                    .collect(Collectors.toList());
//
//        } catch (IOException exception) {
//            log.error("err", exception);
//            return Collections.emptyList();
//        }
//    }
//
//    private List<Map> resolveMapList(final String mapText) {
//
//        if (mapText.startsWith("bo")) {
//            return IntStream.rangeClosed(1, Integer.parseInt(mapText.substring(2)))
//                    .boxed()
//                    .map(x -> Map.UNKNOWN)
//                    .collect(Collectors.toList());
//        }
//
//        return Collections.singletonList(Map.resolveFromShorthand(mapText));
//    }
//
//    public Optional<Team> getRoster(final int teamIdentifier) {
//
//        /*
//        storage:
//        --------
//        url resource
//        jsoup requirement
//        mapper of internal request to HLTVTeamDataRetriever entities (e.g. team string to id).
//        document in, internal model out. Function<Document, T>; @FunctionalInterface of parseDocumentToModel?
//         */
//        try {
//
//            // All Astralis players.
//            final Document document = connectionFactory.getConnection("https://www.hltv.org/team/6665/astralis")
//                    .get();
//
//            HLTVDocumentParser<Team> parser = new TeamRosterParser();
//            return Optional.of(parser.parse(document));
//
//        } catch (IOException exception) {
//            log.error("err", exception);
//            return Optional.empty();
//        }
//    }
//
//    private static ZonedDateTime convertEpochToZonedDateTime(final String epochString) {
//        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(epochString)), ZoneOffset.UTC);
//    }
}
