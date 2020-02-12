package at.spin2time.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

/**
 * Handler to remove the relation between the users S2T-Account and his voiceprofile
 */
public class RemovePersonalizationIntentHandler extends IntentHandler {


    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("RemovePersonalizationIntent"));
    }

    /**
     * Method to get the requested intents name
     * @return IntentName
     */
    @Override
    public String getIntentRequestName() {
        return "RemovePersonalizationIntent";
    }

    /**
     * Method to handle requests that are not personalized
     */
    @Override
    public Optional<Response> handleWithoutPersInfo() {
        return handlerInput.getResponseBuilder()
                .withSpeech("Sie haben noch kein Stimmprofil angelegt. Die Verbindung konnte nicht gelöscht werden.")
                .withSimpleCard("Entfernen fehlgeschlagen", "Kein Stimmprofil erkannt..")
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

        ConnectionClass cc = new ConnectionClass();
        String username = cc.getUserFromVoiceId(personId);

        if(cc.removeVoiceProfile(username)) {
            return handlerInput.getResponseBuilder()
                    .withSpeech("Das Stimmprofil von "+username+" wurde erfolgreich vom Spin2Time Account gelöscht.")
                    .withSimpleCard("Entfernen erfolgreich", "Stimmprofil wurde aus Ihrem Benutzerkonto entfernt.")
                    .withShouldEndSession(false)
                    .build();
        }
        else{
            return handlerInput.getResponseBuilder()
                    .withSpeech("Leider ist beim Entfernen Ihres Stimmprofils ein Fehler aufgetreten. Versuchen Sie es später erneut. ")
                    .withSimpleCard("Entfernen fehlgeschlagen","Ihr Stimmprofil konnte leider nicht von Ihrem S2T-Account entfernt werden.")
                    .withShouldEndSession(true)
                    .build();
        }

    }

}
