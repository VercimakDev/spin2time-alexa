package at.spin2time.main;

import at.spin2time.handlers.*;
import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;

public class Spin2TimeStreamHandler extends SkillStreamHandler {

    private static Skill getSkill(){
        return Skills.standard()
                .addRequestHandlers(
                    new CancelAndStopIntentHandler(),
                    new HelpIntentHandler(),
                    new LaunchRequestHandler(),
                    new SessionEndedRequestHandler(),
                    new StartTimeTrackingIntentHandler(),
                    new StopTimeTrackingIntentHandler(),
                        new ListProjectsIntentHandler())
                .build();
    }

    public Spin2TimeStreamHandler(){
        super(getSkill());
    }

}
