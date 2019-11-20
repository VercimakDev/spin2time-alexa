package at.spin2time.handlers;

import at.spin2time.exceptions.S2TRunntimeException;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

import java.sql.SQLException;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class StopTimeTrackingIntentHandler implements IntentRequestHandler {


    @Override
    public boolean canHandle(HandlerInput input, IntentRequest intentRequest) {
        return input.matches(intentName("StopTimeTrackingIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input, IntentRequest intentRequest) {
        Intent intent = intentRequest.getIntent();
        String username = intent.getSlots().get("name").getValue();
        ConnectionClass c = new ConnectionClass();
        if(!c.userExists(username)) {

            return input.getResponseBuilder()
                    .withSpeech("Der User "+ username + ", konnte nicht gefunden werden")
                    .withSimpleCard("Spin2Time","Zeitaufzeichnung für "+ username + " mit fehler beendet")
                    .build();

        }
        if (!c.checkDoubleEntry(username)) {
            return input.getResponseBuilder()
                    .withSpeech("Der User "+ username + ", hat noch keine Zeitaufzeichnung gestartet")
                    .withSimpleCard("Spin2Time","Zeitaufzeichnung für "+ username + " mit fehler beendet")
                    .build();
        }
            stopTimetracking(username);
            return input.getResponseBuilder()
                    .withSpeech("Vielen Dank "+ username + ", ich wuensche Ihnen einen entspannten Feierabend!")
                    .withSimpleCard("Spin2Time","Zeitaufzeichnung für "+ username + " erfolgreich beendet")
                    .build();

    }
    public void stopTimetracking(String name) {
            ConnectionClass c = new ConnectionClass();
            TimeManagmentClass time = new TimeManagmentClass();
            c.stopTimeTracking(name, time.getNow());
        }


}
