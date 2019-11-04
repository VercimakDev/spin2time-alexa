package at.spin2time.handlers;

import java.sql.*;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.*;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class StartTimeTrackingIntentHandler implements IntentRequestHandler {


    @Override
    public boolean canHandle(HandlerInput input, IntentRequest intentRequest) {
        return input.matches(intentName("StartTimeTrackingIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input, IntentRequest intentRequest) {

        Intent intent = intentRequest.getIntent();

        String username = intent.getSlots().get("name").getValue();
        String projectId = intent.getSlots().get("projectname").getValue();

        StartTimeTracking(username, projectId);

        return input.getResponseBuilder()
                .withSpeech("Danke "+username+". Ihre Zeitaufzeichnung wurde erfolgreich gestartet!")
                .withSimpleCard("Spin2Time", "Zeitaufzeichnung für "+username+" wurde erfolgreich gestartet.")
                .build();
    }

    public void StartTimeTracking(String name, String projectId){
        ConnectionClass c = new ConnectionClass();
        TimeManagmentClass time = new TimeManagmentClass();
        c.startTimeTracking(name,time.getNow(),projectId);
    }

}