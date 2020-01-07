package at.spin2time.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class MonthTimeIntentHandler implements IntentRequestHandler {
    @Override
    public boolean canHandle(HandlerInput handlerInput, IntentRequest intentRequest) {
        return handlerInput.matches(intentName("MonthTimeIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input, IntentRequest intentRequest) {
        Intent intent = intentRequest.getIntent();
        ConnectionClass c = new ConnectionClass();
        String username = intent.getSlots().get("name").getValue();
        String projectname = intent.getSlots().get("projectname").getValue();
        /**
         * We have to check if the user exists
         */
        if(!c.userExists(username)) {

            return input.getResponseBuilder()
                    .withSpeech("Der User " + username + ", konnte nicht gefunden werden")
                    .withSimpleCard("Spin2Time", "Zeitabfrage für " + username + " mit fehler beendet")
                    .build();

        }
        if(!c.isProjectMember(username, projectname)){

            return input.getResponseBuilder()
                    .withSpeech("Der User " + username + ", ist kein Mitglied dieses Projekts")
                    .withSimpleCard("Spin2Time", "Zeitabfrage für " + username + " mit fehler beendet")
                    .build();

        }
        String text = MonthTime(username, projectname);
        return input.getResponseBuilder()
                .withSpeech(text)
                .withSimpleCard("Spin2Time", "CurrentTime Abfrage erfolgrecih ausgeführt")
                .build();
    }

    public String MonthTime(String name, String project) {
        ConnectionClass c = new ConnectionClass();
        return c.getTimeWorkedThisMonth(name, project);
    }
}
