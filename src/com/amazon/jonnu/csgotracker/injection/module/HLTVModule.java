package com.amazon.jonnu.csgotracker.injection.module;

import com.amazon.jonnu.csgotracker.storage.TeamDataRetriever;
import com.amazon.jonnu.csgotracker.storage.hltv.HLTVTeamDataRetriever;
import com.google.inject.AbstractModule;

import com.amazon.jonnu.csgotracker.storage.hltv.TeamIndex;
import com.amazon.jonnu.csgotracker.storage.hltv.InMemoryTeamIndex;

public class HLTVModule extends AbstractModule {

    protected void configure() {
        //bind(TeamIndex.class).to(InMemoryTeamIndex.class);


        bind(TeamDataRetriever.class).to(HLTVTeamDataRetriever.class);
    }

}
