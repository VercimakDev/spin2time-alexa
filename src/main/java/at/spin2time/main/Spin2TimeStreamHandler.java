package at.spin2time.main;

import at.spin2time.handlers.*;
import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;

/**
 * This class initializes the RequestHandlers and adds the skill-id
 */
public class Spin2TimeStreamHandler extends SkillStreamHandler {

    private static Skill getSkill(){
        return Skills.standard()
                .withSkillId("amzn1.ask.skill.9fc5e41f-783f-4db9-924f-76222edb2b51")
                .addRequestHandlers(
                    new StopIntentHandler(),
                    new HelpIntentHandler(),
                    new LaunchRequestHandler(),
                    new SessionEndedRequestHandler(),
                    new StartPersTimeTrackingIntentHandler(),
                    new StopNonpersTimeTrackingIntentHandler(),
                    new RemovePersonalizationIntentHandler(),
                    new StopPersTimeTrackingIntentHandler(),
                        new CurrentTimeIntentHandler(),
                        new PersonalizationIntentHandler(),
                    new MonthTimeIntentHandler(),
                    new StartNonpersTimeTrackingIntentHandler(),
                    new CurrentTimeNonpersIntentHandler(),
                    new CurrentTimePersIntentHandler(),
                    new ListProjectPersIntent(),
                    new ListProjectNopersIntentHandler(),
                    new UnknownRequestHandler())
                .build();
    }

    public Spin2TimeStreamHandler(){
        super(getSkill());
    }

}
