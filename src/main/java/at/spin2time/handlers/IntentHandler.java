package at.spin2time.handlers;

import at.spin2time.exceptions.S2TPersonalizationException;
import at.spin2time.personalization.PersonalizationExtractor;
import at.spin2time.personalization.PersonalizationInfo;
import at.spin2time.util.SessionUtil;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;

import static at.spin2time.util.SessionAttribute.PRINCIPLE_ID;

@Log4j2
public abstract class IntentHandler extends BaseHandler{

    protected PersonalizationInfo personalizationInfo;

    @Override
    public String getRequestName(){
        return getIntentRequestName();
    }

    @Override
    public Optional<Response> handle(){
        String personId = personalizationInitialization();
        if(personId != null){
            try {
                //log.info("PersonID: " + personId);
                return handleWithPersInfo(personId);
            }catch(Exception e){
                throw new S2TPersonalizationException("Couldn't handle Request with PersonID.");
            }
        }
        else{
            //log.info("No Person recognized. Handling without Personalization.");
            return handleWithoutPersInfo();
        }
    }

    private String personalizationInitialization(){
        this.personalizationInfo = PersonalizationExtractor.extractPersonalizationInfoFromRequest(handlerInput);
        addOrUpdateSessionAttribute(PRINCIPLE_ID, personalizationInfo.getPrincipleId());

        //log.info("Personalization status: "+personalizationInfo);
        if(personalizationInfo.isPersonalized()){
            //log.info("Saving userId and personId");
            /**
             * Add a thread that stores the UserId and PersonId to a Database
             * Left out because we created another method that creates the relation between the voice-profile and the
             * s2t user
             */
            //log.info("UserId: "+personalizationInfo.getUserId()+" PersonId: "+personalizationInfo.getPersonId());
            return personalizationInfo.getPersonId();
        }
        return null;
    }

    public abstract String getIntentRequestName();

    protected void addOrUpdateSessionAttribute(String key, Object value){
        SessionUtil.addOrUpdateSessionAttribute(this.attributesManager, key, value);
    }

    public abstract Optional<Response> handleWithoutPersInfo();

    public abstract Optional<Response> handleWithPersInfo(final String personId);

}
