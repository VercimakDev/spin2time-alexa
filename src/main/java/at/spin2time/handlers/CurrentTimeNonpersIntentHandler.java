package at.spin2time.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazon.ask.request.exception.handler.impl.AbstractHandlerInput;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class CurrentTimeNonpersIntentHandler extends IntentHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("CurrentTimeNonpersIntent"));
    }
    @Override
    public String getIntentRequestName() {
        return "CurrentTimeNonpersIntent";
    }

    @Override
    public Optional<Response> handleWithoutPersInfo() {
        ConnectionClass c = new ConnectionClass();
        String username = getUsernameFromRequest();
        /**
         * We have to check if the user exists
         */
        if(!c.userExists(username)) {

            return handlerInput.getResponseBuilder()
                    .withSpeech("Der User "+ username + ", konnte nicht gefunden werden")
                    .withSimpleCard("Spin2Time","Zeitabfrage für "+ username + " mit fehler beendet")
                    .build();

        }
        /**
         * We have to check if the user has a running time recording
         */
        if(!c.checkDoubleEntry(username)) {
            return handlerInput.getResponseBuilder()
                    .withSpeech("Sie haben zur Zeit keine laufende Zeitaufzeichnung")
                    .withSimpleCard("Spin2Time", "Keine Zeitabfrage möglich")
                    .build();
        }
        /**
         * if everything is set like it should be, run this code
         */
        String timeWorked = getCurrentTime(username);
        return handlerInput.getResponseBuilder()
                .withSpeech(timeWorked)
                .withSimpleCard("Spin2Time", "CurrentTime Abfrage erfolgrecih ausgeführt")
                .build();
    }

    @Override
    public Optional<Response> handleWithPersInfo(String personId) {
        ConnectionClass c = new ConnectionClass();
        String username = getUsernameFromRequest();
        /**
         * We have to check if the user exists
         */
        if(!c.userExists(username)) {

            return handlerInput.getResponseBuilder()
                    .withSpeech("Der User "+ username + ", konnte nicht gefunden werden")
                    .withSimpleCard("Spin2Time","Zeitabfrage für "+ username + " mit fehler beendet")
                    .build();

        }
        /**
         * We have to check if the user has a running time recording
         */
        if(!c.checkDoubleEntry(username)) {
            return handlerInput.getResponseBuilder()
                    .withSpeech("Sie haben zur Zeit keine laufende Zeitaufzeichnung")
                    .withSimpleCard("Spin2Time", "Keine Zeitabfrage möglich")
                    .build();
        }
        /**
         * if everything is set like it should be, run this code
         */
        String timeWorked = getCurrentTime(username);
        return handlerInput.getResponseBuilder()
                .withSpeech(timeWorked)
                .withSimpleCard("Spin2Time", "CurrentTime Abfrage erfolgrecih ausgeführt")
                .build();
    }

    /**
     * This function just uses the ConnectionClass to run all needed queries
     * @param name the username
     * @return returns this time the user recorded to this point
     */
    public String getCurrentTime(String name) {
        ConnectionClass c = new ConnectionClass();
        String text = c.getTimeWorkedTillNow(name);
        return text;
    }
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
