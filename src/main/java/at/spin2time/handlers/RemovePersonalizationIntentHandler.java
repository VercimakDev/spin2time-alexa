package at.spin2time.handlers;

import com.amazon.ask.model.Response;

import java.util.Optional;

/**
 * Handler to remove the relation between the users S2T-Account and his voiceprofile
 */
public class RemovePersonalizationIntentHandler extends IntentHandler {

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
                .withSpeech("Sie haben kein Voice-Profil angelegt oder Sie haben noch kein Stimmprofil angelegt.. Die Verbindung konnte nicht gelöscht werden.")
                .withSimpleCard("Verbindung fehlgeschlagen", "Kein Stimmprofil erkannt..")
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

        if(!cc.userExists(username)){
            return handlerInput.getResponseBuilder()
                    .withSpeech("Leider habe ich Ihre Stimme nicht erkannt.")
                    .withShouldEndSession(false)
                    .build();
        }
        else{
            if(cc.removeVoiceProfile(username)) {
                return handlerInput.getResponseBuilder()
                        .withSpeech("Ihr Stimmprofil wurde erfolgreich von Ihrem Benutzerkonto entfernt.")
                        .withSimpleCard("Entfernen erfolgreich", "Stimmprofil wurde aus Ihrem Benutzerkonto entfernt.")
                        .withShouldEndSession(false)
                        .build();
            }
            else{
                return handlerInput.getResponseBuilder()
                        .withSpeech("Leider ist beim entfernen Ihres Stimmprofils ein Fehler aufgetreten. Versuchen Sie es später erneut. ")
                        .withSimpleCard("Entfernen fehlgeschlagen","Ihr Stimmprofil konnte leider nicht von Ihrem S2T-Account entfernt werden.")
                        .withShouldEndSession(true)
                        .build();
            }
        }
    }

}
