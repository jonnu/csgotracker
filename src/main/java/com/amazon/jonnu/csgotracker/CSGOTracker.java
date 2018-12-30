package com.amazon.jonnu.csgotracker;

import com.google.inject.Guice;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.jonnu.csgotracker.injection.CSGOTrackerModule;

public class CSGOTracker extends SkillStreamHandler {

    public CSGOTracker() {
        super(Guice.createInjector(new CSGOTrackerModule()).getInstance(Skill.class));
    }

}
