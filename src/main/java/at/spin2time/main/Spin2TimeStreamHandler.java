package at.spin2time.main;

import at.spin2time.handlers.*;
import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;

public class Spin2TimeStreamHandler extends SkillStreamHandler {

    private static Skill getSkill(){
        return Skills.standard()
                .withSkillId("amzn1.ask.skill.9fc5e41f-783f-4db9-924f-76222edb2b51")
                .addRequestHandlers(
                    new CancelIntentHandler(),
                    new HelpIntentHandler(),
                    new LaunchRequestHandler(),
                    new SessionEndedRequestHandler(),
                    new StartTimeTrackingIntentHandler(),
                    new StopTimeTrackingIntentHandler(),
                        new ListProjectsIntentHandler(),
                        new CurrentTimeIntentHandler(),
                        new PersonalizationIntentHandler())
                .build();
    }

    public Spin2TimeStreamHandler(){
        super(getSkill());
    }

}
