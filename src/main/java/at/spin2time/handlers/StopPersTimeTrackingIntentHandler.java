package at.spin2time.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class StopPersTimeTrackingIntentHandler extends IntentHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("StopPersTimeTrackingIntent"));
    }

    public void stopTimetracking(String name) {
            ConnectionClass c = new ConnectionClass();
            TimeManagmentClass time = new TimeManagmentClass();
            c.stopTimeTracking(name, time.getNow());
    }


    @Override
    public String getIntentRequestName() {
        return "StopPersTimeTrackingIntent";
    }

    @Override
    public Optional<Response> handleWithoutPersInfo() {
        return handlerInput.getResponseBuilder()
                .withShouldEndSession(false)
                .withSpeech("Um diese Funktion zu nutzen muessen Sie Ihren Nutzernamen angeben oder ein Voice-Profil erstellen.")
                .withReprompt("Versuchen Sie beispielsweise: 'Hannes geht jetzt nach Hause'")
                .build();
    }

    @Override
    public Optional<Response> handleWithPersInfo(String personId) {
        ConnectionClass cc = new ConnectionClass();
        String username = cc.getUserFromVoiceId(personId);
        if(!cc.userExists(username)) {
            return handlerInput.getResponseBuilder()
                    .withSpeech("Der User "+ username + ", konnte nicht gefunden werden")
                    .withSimpleCard("Spin2Time","Zeitaufzeichnung für "+ username + " mit fehler beendet")
                    .build();

        }
        else if (!cc.checkDoubleEntry(username)) {
            return handlerInput.getResponseBuilder()
                    .withSpeech("Der User "+ username + ", hat noch keine Zeitaufzeichnung gestartet")
                    .withSimpleCard("Spin2Time","Zeitaufzeichnung für "+ username + " mit fehler beendet")
                    .build();
        }
        else {
            stopTimetracking(username);
            return handlerInput.getResponseBuilder()
                    .withSpeech("Ich wuensche Ihnen einen entspannten Feierabend, "+username+"!")
                    .withSimpleCard("Spin2Time", "Zeitaufzeichnung für " + username + " erfolgreich beendet")
                    .build();
        }
    }
}
