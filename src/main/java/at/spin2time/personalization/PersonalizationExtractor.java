package at.spin2time.personalization;

import at.spin2time.exceptions.S2TPersonalizationException;
import at.spin2time.util.SessionUtil;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Context;
import com.amazon.ask.model.Person;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.interfaces.system.SystemState;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

import static at.spin2time.util.SessionAttribute.PRINCIPLE_ID;

@Log4j2
public class PersonalizationExtractor {

    private static final String PERSONID_PREFIX = "amzn1.ask.person";

    /**
     * Method returns the PersonalizationInfo of an IntentRequest
     * @param handlerInput
     * @return Returns the PersonalizationInfo of an request
     */
    public static PersonalizationInfo extractPersonalizationInfoFromRequest(HandlerInput handlerInput){

        if(handlerInput == null){
            throw new S2TPersonalizationException("Handlerinput can't be null");
        }

        final String userId = handlerInput.getRequestEnvelope().getSession().getUser().toString();
        final String principleId = SessionUtil.getSessionAttribute(handlerInput.getAttributesManager(), PRINCIPLE_ID, String.class);
        if(StringUtils.isNotBlank(principleId)){
            return new PersonalizationInfo(principleId, principleId, userId, principleId.startsWith(PERSONID_PREFIX));
        }
        return Optional.of(handlerInput)
                .map(HandlerInput::getRequestEnvelope)
                .map(RequestEnvelope::getContext)
                .map(Context::getSystem)
                .map(SystemState::getPerson)
                .map(Person::getPersonId)
                .filter(StringUtils::isNotBlank)
                .map((pid) -> {
                    log.info("PersonID is: "+pid);
                    return new PersonalizationInfo(pid, pid, userId, true);
                }).orElseGet(() -> {
                    log.info("PersonID does not exist, using userID as principleID");
                    return new PersonalizationInfo(null, userId, StringUtils.EMPTY, false);
                });
    }
}
