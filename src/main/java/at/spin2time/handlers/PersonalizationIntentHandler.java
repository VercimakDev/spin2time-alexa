package at.spin2time.handlers;

import com.amazon.ask.model.*;
import com.amazon.ask.request.exception.handler.impl.AbstractHandlerInput;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;


/**
 * Handler to create a relation between s2t database and the Users voice-profile
 */
@Log4j2
public class PersonalizationIntentHandler extends IntentHandler {

    /**
     * Method to get the requested intents name
     * @return IntentName
     */
    @Override
    public String getIntentRequestName() {
        return "PersonalizationIntent";
    }

    /**
     * Method to handle requests that are not personalized
     */
    @Override
    public Optional<Response> handleWithoutPersInfo() {
        log.error("No personId found. Request isn't personalized.");
        return handlerInput.getResponseBuilder()
                .withSpeech("Sie haben kein Voice-Profil angelegt. Ihr Benutzerkonto konnte nicht mit ihrer Stimme verknuepft werden.")
                .withSimpleCard("Verbindung fehlgeschlagen", "Benutzerkonto konnte nicht mit Stimmprofil verbunden werden.")
                .withShouldEndSession(false)
                .build();
    }

    /**
     * Method that handles personalized requests
     * @param personId users personId from Alexa request to identify the user
     * @return Alexa response
     */
    @Override
    public Optional<Response> handleWithPersInfo(String personId) {

        final String username = getUsernameFromRequest();
        ConnectionClass cc = new ConnectionClass();

        if(!cc.userExists(username) || getUsernameFromRequest() == null){
            log.error("User doesn't exist");
            return handlerInput.getResponseBuilder()
                    .withSpeech("Der angegebene Benutzername existiert nicht oder ihr Benutzername wurde nicht erkannt.")
                    .withShouldEndSession(false)
                    .build();
        }
        else if(cc.hasPersonId(username)){
            log.error("User "+username+" already has an personId");
            return handlerInput.getResponseBuilder()
                    .withSpeech("Ihr Benutzerkonto wurde bereits mit ihrem Stimmprofil verknuepft.")
                    .withShouldEndSession(false)
                    .build();
        }
        else{
            cc.saveUserRelation(username, personId);
            log.info("Successfully made relation");
            return handlerInput.getResponseBuilder()
                    .withSpeech("Ihr Stimmprofil wurde erfolgreich ihrem Benutzerkonto zugeordnet.")
                    .withSimpleCard("Benutzerzuordnung erfolgreich", "Benutzerkonto wurde mit Stimmprofil verbunden.")
                    .withShouldEndSession(false)
                    .build();
        }
    }

    /**
     * Method to get the slot value from the intentRequest
     * @return value of slot username
     */
    private String getUsernameFromRequest(){
        return Optional.of(handlerInput)
                .map(AbstractHandlerInput::getRequest)
                .map(i -> (IntentRequest) i)
                .map(IntentRequest::getIntent)
                .map(Intent::getSlots)
                .map(s -> s.get("name"))
                .map(Slot::getValue)
                .orElse(null);
    }

}
