package com.amazon.jonnu.csgotracker.injection.module;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;

import com.amazon.jonnu.csgotracker.model.Renderable;
import com.amazon.jonnu.csgotracker.model.TeamRoster;
import com.amazon.jonnu.csgotracker.storage.ResourceMapper;
import com.amazon.jonnu.csgotracker.storage.TeamDataRetriever;
import com.amazon.jonnu.csgotracker.storage.hltv.HLTVDataDelegate;
import com.amazon.jonnu.csgotracker.storage.hltv.HLTVDataDelegateImpl;
import com.amazon.jonnu.csgotracker.storage.hltv.HLTVDocumentParserFactory;
import com.amazon.jonnu.csgotracker.storage.hltv.HLTVDocumentParserFactoryImpl;
import com.amazon.jonnu.csgotracker.storage.hltv.HLTVDocumentRetriever;
import com.amazon.jonnu.csgotracker.storage.hltv.HLTVDocumentRetrieverImpl;
import com.amazon.jonnu.csgotracker.storage.hltv.HLTVTeamDataRetriever;
import com.amazon.jonnu.csgotracker.storage.hltv.InMemoryTeamIndex;
import com.amazon.jonnu.csgotracker.storage.hltv.parser.HLTVDocumentParser;
import com.amazon.jonnu.csgotracker.storage.hltv.parser.TeamRosterParser;

public class HLTVModule extends AbstractModule {

    protected void configure() {
        //bind(TeamIndex.class).to(InMemoryTeamIndex.class);


        bind(TeamDataRetriever.class).to(HLTVTeamDataRetriever.class);

        bind(HLTVDataDelegate.class).to(HLTVDataDelegateImpl.class);
        bind(HLTVDocumentRetriever.class).to(HLTVDocumentRetrieverImpl.class);
        bind(HLTVDocumentParserFactory.class).to(HLTVDocumentParserFactoryImpl.class);

        bind(new TypeLiteral<ResourceMapper<Integer>>() {}).to(InMemoryTeamIndex.class);

        // Parsers
        MapBinder<Class<? extends Renderable>, HLTVDocumentParser<? extends Renderable>> documentParsers = MapBinder.newMapBinder(
                binder(), new TypeLiteral<Class<? extends Renderable>>() {}, new TypeLiteral<HLTVDocumentParser<? extends Renderable>>() {});
        documentParsers.addBinding(TeamRoster.class).to(TeamRosterParser.class);
    }

}
