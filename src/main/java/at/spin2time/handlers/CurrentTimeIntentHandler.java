package at.spin2time.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class CurrentTimeIntentHandler implements IntentRequestHandler {
    @Override
    public boolean canHandle(HandlerInput handlerInput, IntentRequest intentRequest) {
        return handlerInput.matches(intentName("CurrentTimeIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input, IntentRequest intentRequest) {
        Intent intent = intentRequest.getIntent();
        ConnectionClass c = new ConnectionClass();
        String username = intent.getSlots().get("name").getValue();
        /**
         * We have to check if the user exists
         */
        if(!c.userExists(username)) {

            return input.getResponseBuilder()
                    .withSpeech("Der User "+ username + ", konnte nicht gefunden werden")
                    .withSimpleCard("Spin2Time","Zeitabfrage für "+ username + " mit fehler beendet")
                    .build();

        }
        /**
         * We have to check if the user has a running time recording
         */
        if(!c.checkDoubleEntry(username)) {
            return input.getResponseBuilder()
                    .withSpeech("Sie haben zur Zeit keine laufende Zeitaufzeichnung")
                    .withSimpleCard("Spin2Time", "Keine Zeitabfrage möglich")
                    .build();
        }
        /**
         * if everything is set like it should be, run this code
         */
        String timeWorked = getCurrentTime(username);
        return input.getResponseBuilder()
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
}
