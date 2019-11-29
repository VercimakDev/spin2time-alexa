package at.spin2time.handlers;

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

        ConnectionClass c = new ConnectionClass();


        if(!c.userExists(username) && !c.projectExists(projectId)){
            return input.getResponseBuilder()
                    .withSpeech("Leider wurde ihr Benutzername und das Projekt nicht gefunden.")
                    .withSimpleCard("Spin2Time", "Zeitaufzeichnung fuer "+username+" abgebrochen.")
                    .build();
        }
        else if(!c.userExists(username)){
            return input.getResponseBuilder()
                    .withSpeech("Leider wurde ihr Benutzername "+username+" nicht gefunden.")
                    .withSimpleCard("Spin2Time", "Zeitaufzeichnung fuer "+username+" abgebrochen.")
                    .build();
        }
        else if(c.checkDoubleEntry(username)){
            return input.getResponseBuilder()
                    .withSpeech("Der Benutzer "+username+" hat bereits eine Zeitaufzeichnung gestartet. Beenden Sie diese zuerst bevor Sie eine neue starten.")
                    .withSimpleCard("Spin2Time", "Zeitaufzeichnung fuer "+username+" konnte nicht gestartet werden.")
                    .build();
        }
        else if(!c.projectExists(projectId)){
            return input.getResponseBuilder()
                    .withSpeech("Leider wurde das Projekt mit der Nummer "+projectId+" nicht gefunden.")
                    .withSimpleCard("Spin2Time", "Zeitaufzeichnung fuer "+username+" abgebrochen.")
                    .build();
        }
        else if(!c.isProjectMember(username, projectId)){
            return input.getResponseBuilder()
                    .withSpeech("Der Benutzer "+username+" ist leider kein Mitglied vom Projekt mit der Nummer "+projectId)
                    .withSimpleCard("Spin2Time", "Zeitaufzeichnung fuer "+username+" abgebrochen.")
                    .build();
        }
        else{
            startTimeTracking(username,projectId);
            return input.getResponseBuilder()
                    .withSpeech("Danke "+username+". Ihre Zeitaufzeichnung am Projekt "+projectId+" wurde erfolgreich gestartet!")
                    .withSimpleCard("Spin2Time", "Zeitaufzeichnung für "+username+" wurde erfolgreich gestartet.")
                    .build();
        }
    }

    private void startTimeTracking(String name, String projectId){
        ConnectionClass c = new ConnectionClass();
        TimeManagmentClass time = new TimeManagmentClass();
        c.startTimeTracking(name,time.getNow(),projectId);
    }

}