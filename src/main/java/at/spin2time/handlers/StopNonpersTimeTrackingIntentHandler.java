package at.spin2time.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazon.ask.request.exception.handler.impl.AbstractHandlerInput;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class StopNonpersTimeTrackingIntentHandler extends IntentHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("StopNonpersTimeTrackingIntent"));
    }

    public void stopTimetracking(String name) {
        ConnectionClass c = new ConnectionClass();
        TimeManagmentClass time = new TimeManagmentClass();
        c.stopTimeTracking(name, time.getNow());
    }


    @Override
    public String getIntentRequestName() {
        return "StopNonpersTimeTrackingIntent";
    }

    @Override
    public Optional<Response> handleWithoutPersInfo() {
        ConnectionClass cc = new ConnectionClass();
        String username = getUsernameFromRequest();
        if(!cc.userExists(username)) {
            return handlerInput.getResponseBuilder()
                    .withSpeech("Der User "+ username + ", konnte nicht gefunden werden")
                    .withShouldEndSession(false)
                    .withSimpleCard("Spin2Time","Zeitaufzeichnung für "+ username + " mit fehler beendet")
                    .build();

        }
        else if (!cc.checkDoubleEntry(username)) {
            return handlerInput.getResponseBuilder()
                    .withSpeech("Der User "+ username + ", hat noch keine Zeitaufzeichnung gestartet")
                    .withShouldEndSession(false)
                    .withSimpleCard("Spin2Time","Zeitaufzeichnung für "+ username + " mit fehler beendet")
                    .build();
        }
        else {
            stopTimetracking(username);
            return handlerInput.getResponseBuilder()
                    .withSpeech(username + ", ich wuensche Ihnen einen entspannten Feierabend!")
                    .withShouldEndSession(false)
                    .withSimpleCard("Spin2Time", "Zeitaufzeichnung für " + username + " erfolgreich beendet")
                    .build();
        }
    }

    @Override
    public Optional<Response> handleWithPersInfo(String personId) {
        ConnectionClass cc = new ConnectionClass();
        String username = getUsernameFromRequest();
        if(!cc.userExists(username)) {
            return handlerInput.getResponseBuilder()
                    .withSpeech("Der User "+ username + ", konnte nicht gefunden werden")
                    .withShouldEndSession(false)
                    .withSimpleCard("Spin2Time","Zeitaufzeichnung für "+ username + " mit fehler beendet")
                    .build();

        }
        else if (!cc.checkDoubleEntry(username)) {
            return handlerInput.getResponseBuilder()
                    .withSpeech("Der User "+ username + ", hat noch keine Zeitaufzeichnung gestartet")
                    .withShouldEndSession(false)
                    .withSimpleCard("Spin2Time","Zeitaufzeichnung für "+ username + " mit fehler beendet")
                    .build();
        }
        else {
            stopTimetracking(username);
            return handlerInput.getResponseBuilder()
                    .withSpeech(username + ", ich wuensche Ihnen einen entspannten Feierabend!")
                    .withShouldEndSession(false)
                    .withSimpleCard("Spin2Time", "Zeitaufzeichnung für " + username + " erfolgreich beendet")
                    .build();
        }
    }

    /**
     * Method to get the slot value from the intentRequest
     * @return value of slot name
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
