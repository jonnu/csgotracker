package com.amazon.jonnu.csgotracker;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;
import com.amazon.jonnu.csgotracker.handler.*;

public class CSGOTracker extends SkillStreamHandler {

    public CSGOTracker() {
        super(getSkill());
    }

    private static Skill getSkill() {
        return Skills.standard()
                .addRequestHandlers(
                        new GetTeamScheduleHandler(),
                        new CancelAndStopIntentHandler(),
                        new HelpIntentHandler(),
                        new LaunchRequestHandler(),
                        new FallbackIntentHandler(),
                        new SessionEndedRequestHandler()
                )
                .withSkillId("amzn1.ask.skill.f0328fa9-5677-4701-9738-b8608df39bac")
                .build();
    }
}
