package com.amazon.jonnu.csgotracker.injection.module;

import com.google.inject.AbstractModule;

import com.amazon.jonnu.csgotracker.storage.hltv.Team;
import com.amazon.jonnu.csgotracker.storage.hltv.TeamImpl;

public class HLTVModule extends AbstractModule {

    protected void configure() {
        bind(Team.class).to(TeamImpl.class);
    }

}
