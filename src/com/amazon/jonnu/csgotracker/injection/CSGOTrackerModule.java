package com.amazon.jonnu.csgotracker.injection;

import java.util.ArrayList;
import java.util.Set;

import com.amazon.jonnu.csgotracker.service.*;
import com.amazon.jonnu.csgotracker.storage.TeamDataRetriever;
import com.amazon.jonnu.csgotracker.storage.hltv.HLTVTeamDataRetriever;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.multibindings.Multibinder;

import com.amazon.ask.Skill;
import com.amazon.ask.Skills;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.jonnu.csgotracker.handler.CancelAndStopIntentHandler;
import com.amazon.jonnu.csgotracker.handler.FallbackIntentHandler;
import com.amazon.jonnu.csgotracker.handler.GetTeamScheduleHandler;
import com.amazon.jonnu.csgotracker.handler.HelpIntentHandler;
import com.amazon.jonnu.csgotracker.handler.LaunchRequestHandler;
import com.amazon.jonnu.csgotracker.handler.SessionEndedRequestHandler;
import com.amazon.jonnu.csgotracker.injection.module.HLTVModule;
import com.amazon.jonnu.csgotracker.service.alexa.AlexaSettings;
import com.amazon.jonnu.csgotracker.service.alexa.AlexaSettingsImpl;
import com.amazon.jonnu.csgotracker.service.jsoup.ConnectionFactory;
import com.amazon.jonnu.csgotracker.service.jsoup.ConnectionFactoryImpl;
import com.amazon.jonnu.csgotracker.storage.schedule.ScheduleStorage;
import com.amazon.jonnu.csgotracker.storage.schedule.ScheduleStorageImpl;

public class CSGOTrackerModule extends AbstractModule {

    /**
     * TODO: get this out of configuration.
     */
    private static String SKILL_ID = "amzn1.ask.skill.f0328fa9-5677-4701-9738-b8608df39bac";

    protected void configure() {

        final Multibinder<RequestHandler> requestHandlerBinder = Multibinder.newSetBinder(binder(), RequestHandler.class);

        requestHandlerBinder.addBinding().to(GetTeamScheduleHandler.class);
        requestHandlerBinder.addBinding().to(CancelAndStopIntentHandler.class);
        requestHandlerBinder.addBinding().to(HelpIntentHandler.class);
        requestHandlerBinder.addBinding().to(LaunchRequestHandler.class);
        requestHandlerBinder.addBinding().to(FallbackIntentHandler.class);
        requestHandlerBinder.addBinding().to(SessionEndedRequestHandler.class);

        bind(EntityResolver.class).to(EntityResolverImpl.class);
        bind(ScheduleStorage.class).to(ScheduleStorageImpl.class);
        bind(ConnectionFactory.class).to(ConnectionFactoryImpl.class);
        bind(TeamDataRetriever.class).to(HLTVTeamDataRetriever.class);

        bind(AlexaSettings.class).to(AlexaSettingsImpl.class);

        // Services
        bind(TeamSchedule.class).to(TeamScheduleImpl.class);

        install(new HLTVModule());
    }

    @Provides
    private Skill provideCSGOTrackerSkill(final Set<RequestHandler> requestHandlers) {
        return Skills.standard()
                .addRequestHandlers(new ArrayList<>(requestHandlers))
                .withSkillId(SKILL_ID)
                .build();
    }

}
