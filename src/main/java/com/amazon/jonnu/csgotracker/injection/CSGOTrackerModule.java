package com.amazon.jonnu.csgotracker.injection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

import com.amazon.ask.Skill;
import com.amazon.ask.Skills;
import com.amazon.ask.dispatcher.exception.ExceptionHandler;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.dispatcher.request.interceptor.RequestInterceptor;
import com.amazon.ask.dispatcher.request.interceptor.ResponseInterceptor;
import com.amazon.jonnu.csgotracker.handler.core.CancelAndStopIntentHandler;
import com.amazon.jonnu.csgotracker.handler.core.FallbackIntentHandler;
import com.amazon.jonnu.csgotracker.handler.core.HelpIntentHandler;
import com.amazon.jonnu.csgotracker.handler.core.LaunchRequestHandler;
import com.amazon.jonnu.csgotracker.handler.core.SessionEndedRequestHandler;
import com.amazon.jonnu.csgotracker.handler.csgo.GetTeamRosterHandler;
import com.amazon.jonnu.csgotracker.handler.csgo.GetTeamScheduleHandler;
import com.amazon.jonnu.csgotracker.injection.module.HLTVModule;
import com.amazon.jonnu.csgotracker.injection.module.TemplateModule;
import com.amazon.jonnu.csgotracker.model.TeamRoster;
import com.amazon.jonnu.csgotracker.model.TeamSchedule;
import com.amazon.jonnu.csgotracker.service.EntityResolver;
import com.amazon.jonnu.csgotracker.service.EntityResolverImpl;
import com.amazon.jonnu.csgotracker.service.alexa.AlexaSettings;
import com.amazon.jonnu.csgotracker.service.alexa.AlexaSettingsImpl;
import com.amazon.jonnu.csgotracker.service.i18n.I18NStringResolver;
import com.amazon.jonnu.csgotracker.service.i18n.I18NStringResolverImpl;
import com.amazon.jonnu.csgotracker.service.jsoup.DocumentFactory;
import com.amazon.jonnu.csgotracker.service.jsoup.DocumentFactoryImpl;
import com.amazon.jonnu.csgotracker.storage.TeamDataRetriever;
import com.amazon.jonnu.csgotracker.storage.hltv.HLTVTeamDataRetriever;
import com.amazon.jonnu.csgotracker.view.renderer.Renderer;
import com.amazon.jonnu.csgotracker.view.renderer.TeamRosterRenderer;
import com.amazon.jonnu.csgotracker.view.renderer.TeamScheduleRenderer;

public class CSGOTrackerModule extends AbstractModule {

    protected void configure() {

        try {
            final Properties configuration = new Properties();
            configuration.load(getClass().getClassLoader().getResourceAsStream("configuration.properties"));
            Names.bindProperties(binder(), configuration);
        }
        catch (IOException exception) {
            // probably should throw here.
            throw new RuntimeException("failed to load props.");
        }

        final Multibinder<RequestHandler> requestHandlerBinder = Multibinder.newSetBinder(binder(), RequestHandler.class);
        requestHandlerBinder.addBinding().to(GetTeamScheduleHandler.class);
        requestHandlerBinder.addBinding().to(GetTeamRosterHandler.class);
        requestHandlerBinder.addBinding().to(CancelAndStopIntentHandler.class);
        requestHandlerBinder.addBinding().to(HelpIntentHandler.class);
        requestHandlerBinder.addBinding().to(LaunchRequestHandler.class);
        requestHandlerBinder.addBinding().to(FallbackIntentHandler.class);
        requestHandlerBinder.addBinding().to(SessionEndedRequestHandler.class);

        final Multibinder<ExceptionHandler> exceptionHandlerBinder = Multibinder.newSetBinder(binder(), ExceptionHandler.class);
        exceptionHandlerBinder.addBinding().to(com.amazon.jonnu.csgotracker.handler.exception.ExceptionHandler.class);

        final Multibinder<RequestInterceptor> requestInterceptors = Multibinder.newSetBinder(binder(), RequestInterceptor.class);
        final Multibinder<ResponseInterceptor> responseInterceptors = Multibinder.newSetBinder(binder(), ResponseInterceptor.class);

        bind(EntityResolver.class).to(EntityResolverImpl.class);
        bind(DocumentFactory.class).to(DocumentFactoryImpl.class);
        bind(TeamDataRetriever.class).to(HLTVTeamDataRetriever.class);

        bind(AlexaSettings.class).to(AlexaSettingsImpl.class);


        // Renderers
        bind(new TypeLiteral<Renderer<TeamRoster>>() {}).to(TeamRosterRenderer.class);
        bind(new TypeLiteral<Renderer<TeamSchedule>>() {}).to(TeamScheduleRenderer.class);

        bind(I18NStringResolver.class).to(I18NStringResolverImpl.class);

        // Alexa
        bind(AlexaSettings.class).to(AlexaSettingsImpl.class);

        install(new HLTVModule());
        install(new TemplateModule());
    }

    @Provides
    private Skill provideCSGOTrackerSkill(@Named("skill.identifier") final String skillId,
            final Set<RequestHandler> requestHandlers,
            final Set<ExceptionHandler> exceptionHandlers,
            final Set<RequestInterceptor> requestInterceptors,
            final Set<ResponseInterceptor> responseInterceptors) {
        return Skills.standard()
                .withSkillId(skillId)
                .addRequestHandlers(new ArrayList<>(requestHandlers))
                .addExceptionHandlers(new ArrayList<>(exceptionHandlers))
                .addRequestInterceptors(new ArrayList<>(requestInterceptors))
                .addResponseInterceptors(new ArrayList<>(responseInterceptors))
                .build();
    }

}
