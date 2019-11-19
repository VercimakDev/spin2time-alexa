package at.spin2time.handlers;

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
        StopTimetracking(username);


        return input.getResponseBuilder()
                .withSpeech("Vielen Dank "+ username + ", ich wuensche Ihnen einen entspannten Feierabend!")
                .withSimpleCard("Spin2Time","Zeitaufzeichnung für "+ username + " erfolgreich beendet")
                .build();
    }
    public void StopTimetracking(String name) {

            ConnectionClass c = new ConnectionClass();
            TimeManagmentClass time = new TimeManagmentClass();
            c.stopTimeTracking(name,time.getNow());
        }


}
