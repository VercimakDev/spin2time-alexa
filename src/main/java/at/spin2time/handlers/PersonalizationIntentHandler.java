package at.spin2time.handlers;

import at.spin2time.exceptions.S2TRunntimeException;
import at.spin2time.personalization.PersonalizationExtractor;
import at.spin2time.personalization.PersonalizationInfo;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.response.ResponseBuilder;
import lombok.extern.log4j.Log4j;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;
import static com.amazon.ask.request.Predicates.requestType;

/**
 * Handler to create a relation between s2t database and the Users voice-profile
 */
@Log4j
public class PersonalizationIntentHandler implements IntentRequestHandler {


    @Override
    public boolean canHandle(HandlerInput input, IntentRequest intentRequest) {
        return input.matches(intentName("PersonalizationIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input, IntentRequest intentRequest) {

        Intent intent = intentRequest.getIntent();
        String username = intent.getSlots().get("name").getValue();
        PersonalizationInfo persInfo = PersonalizationExtractor.extractPersonalizationInfoFromRequest(input);
        String personId = persInfo.getPersonId();

        if(personId == "" || persInfo.getPrincipleId() == persInfo.getUserId()){
            log.error("No personId found. Request isn't personalized.");
            return input.getResponseBuilder()
                    .withSpeech("Sie haben kein Voice-Profil angelegt. Ihr Benutzerkonto konnte nicht mit ihrer Stimme verknuepft werden.")
                    .withSimpleCard("Verbindung fehlgeschlagen", "Benutzerkonto konnte nicht mit Stimmprofil verbunden werden.")
                    .withShouldEndSession(false)
                    .build();
        }
        else{
            try {
                ConnectionClass cc = new ConnectionClass();
                int res = cc.saveUserRelation(username, personId);
                if(res == 0) {
                    log.info("Successfully made relation");
                    return input.getResponseBuilder()
                            .withSpeech("Ihr Stimmprofil wurde erfolgreich ihrem Benutzerkonto zugeordnet.")
                            .withSimpleCard("Benutzerzuordnung erfolgreich", "Benutzerkonto wurde mit Stimmprofil verbunden.")
                            .withShouldEndSession(false)
                            .build();
                }
                else if(res == 1){
                    log.error("Exception occurs when trying to update user entry in database");
                    return input.getResponseBuilder()
                            .withSpeech("Fehler bei der Eintragung in die Datenbank. Versuchen Sie es spaeter erneut")
                            .withShouldEndSession(false)
                            .build();
                }
                else if(res == 2){
                    log.error("User doesn't exist");
                    return input.getResponseBuilder()
                            .withSpeech("Der angegebene Benutzername existiert nicht.")
                            .withShouldEndSession(false)
                            .build();
                }
                else if(res == 3){
                    log.error("User "+username+" already has an personId");
                    return input.getResponseBuilder()
                            .withSpeech("Ihr Benutzerkonto wurde bereits mit ihrem Stimmprofil verknuepft.")
                            .withShouldEndSession(false)
                            .build();
                }
            }catch(Exception e){
                log.error(new S2TRunntimeException("cc.saveUserRelation failed"));
                return input.getResponseBuilder()
                        .withSpeech("Ein Fehler ist bei Ihrer Anfrage aufgetreten. Versuchen Sie es spaeter erneut.")
                        .withSimpleCard("Fehler", "Es ist ein Fehler bei der Kommunikation mit dem Server aufgetreten. Versuchen Sie es spaeter erneut.")
                        .withShouldEndSession(false)
                        .build();
            }
        }
        return input.getResponseBuilder()
                .withSpeech("Es tut mir leid. Es ist ein Feher aufgetreten.")
                .withSimpleCard("Verbindung fehlgeschlagen", "Benutzerkonto konnte nicht mit Stimmprofil verbunden werden.")
                .withShouldEndSession(false)
                .build();
    }
}
