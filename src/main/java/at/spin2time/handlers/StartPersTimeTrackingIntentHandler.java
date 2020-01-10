package at.spin2time.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.*;
import com.amazon.ask.request.exception.handler.impl.AbstractHandlerInput;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;
import static com.amazon.ask.request.Predicates.requestType;

/**
 * Handler to start time tracking for a specific project
 * Uses skill personalization
 */
@Log4j2
public class StartPersTimeTrackingIntentHandler extends IntentHandler{

    @Override
    public boolean canHandle(HandlerInput input) {
        log.info("Request can be handled by StartPersTimeTrackingIntentHandler");
        return input.matches(intentName("StartPersTimeTrackingIntent"));
    }

    /**
     * Method to get the Intent-requests name
     * @return requestname
     */
    @Override
    public String getIntentRequestName() {
        return "StartTimeTrackingIntent";
    }

    /**
     * Handles the request without personalization info
     * @return Alexa response
     */
    @Override
    public Optional<Response> handleWithoutPersInfo() {

        return handlerInput.getResponseBuilder()
                .withShouldEndSession(false)
                .withSpeech("Um diese Funktion zu nutzen, muessen Sie einen Nutzernamen angeben oder ein Voice-Profil erstellen.")
                .withReprompt("Versuchen Sie beispielsweise: 'Hannes arbeitet jetzt'")
                .build();

    }

    /**
     * Handles the request with personalization info
     * @param personId the users unique personid
     * @return personalized response
     */
    @Override
    public Optional<Response> handleWithPersInfo(String personId) {

        ConnectionClass cc = new ConnectionClass();
        String username = cc.getUserFromVoiceId(personId);
        String projectId = getProjectidFromRequest();

        return getResponse(cc, username, projectId);
    }

    /**
     * Method to reduce duplicate response code
     * @param c ConnectionClass object
     * @param username Username of user
     * @param projectId ProjectId of project
     * @return Alexa response
     */
    private Optional<Response> getResponse(ConnectionClass c, String username, String projectId) {
        if(!c.userExists(username) && !c.projectExists(projectId)){
            log.error("Username and ProjectId not found");
            return handlerInput.getResponseBuilder()
                    .withSpeech("Leider wurde ihr Benutzername und das Projekt nicht gefunden.")
                    .withSimpleCard("Spin2Time", "Zeitaufzeichnung fuer "+username+" abgebrochen.")
                    .build();
        }
        else if(!c.userExists(username)){
            log.error("Username "+username+" not found");
            return handlerInput.getResponseBuilder()
                    .withSpeech("Leider wurde ihr Benutzername "+username+" nicht gefunden.")
                    .withSimpleCard("Spin2Time", "Zeitaufzeichnung fuer "+username+" abgebrochen.")
                    .build();
        }
        else if(c.checkDoubleEntry(username)){
            log.error(username+" has already started time tracking.");
            return handlerInput.getResponseBuilder()
                    .withSpeech("Der Benutzer "+username+" hat bereits eine Zeitaufzeichnung gestartet. Beenden Sie diese zuerst bevor Sie eine neue starten.")
                    .withSimpleCard("Spin2Time", "Zeitaufzeichnung fuer "+username+" konnte nicht gestartet werden.")
                    .build();
        }
        else if(!c.projectExists(projectId)){
            log.error("Project "+projectId+" not found");
            return handlerInput.getResponseBuilder()
                    .withSpeech("Leider wurde das Projekt mit der Nummer "+projectId+" nicht gefunden.")
                    .withSimpleCard("Spin2Time", "Zeitaufzeichnung fuer "+username+" abgebrochen.")
                    .build();
        }
        else if(!c.isProjectMember(username, projectId)){
            log.error(username+" is no teammember of project "+projectId);
            return handlerInput.getResponseBuilder()
                    .withSpeech("Der Benutzer "+username+" ist leider kein Mitglied vom Projekt mit der Nummer "+projectId)
                    .withSimpleCard("Spin2Time", "Zeitaufzeichnung fuer "+username+" abgebrochen.")
                    .build();
        }
        else{
            log.info("Started time tracking for user "+username+" on project "+projectId);
            startTimeTracking(username,projectId);
            return handlerInput.getResponseBuilder()
                    .withSpeech("Danke "+username+". Ihre Zeitaufzeichnung am Projekt "+projectId+" wurde erfolgreich gestartet!")
                    .withSimpleCard("Spin2Time", "Zeitaufzeichnung für "+username+" wurde erfolgreich gestartet.")
                    .build();
        }
    }


    /**
     * Method to get the slot value from the intentRequest
     * @return value of slot projectname
     */
    private String getProjectidFromRequest(){
        return Optional.of(handlerInput)
                .map(AbstractHandlerInput::getRequest)
                .map(i -> (IntentRequest) i)
                .map(IntentRequest::getIntent)
                .map(Intent::getSlots)
                .map(s -> s.get("projectname"))
                .map(Slot::getValue)
                .orElse(null);
    }

    /**
     * Method which starts time-tracking
     * @param name the users name
     * @param projectId of project for which the user would like to start time-tracking
     */
    private void startTimeTracking(String name, String projectId){
        ConnectionClass c = new ConnectionClass();
        TimeManagmentClass time = new TimeManagmentClass();
        c.startTimeTracking(name,time.getNow(),projectId);
    }

}