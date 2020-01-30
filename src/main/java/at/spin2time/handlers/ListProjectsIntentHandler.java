package at.spin2time.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

import java.util.List;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class ListProjectsIntentHandler implements IntentRequestHandler {

    ConnectionClass cc = new ConnectionClass();

    @Override
    public boolean canHandle(HandlerInput input, IntentRequest intentRequest) {
        return input.matches(intentName("ListProjectsIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input, IntentRequest intentRequest) {

        Intent intent = intentRequest.getIntent();

        String username = intent.getSlots().get("name").getValue();

        String speechtext = getProjects(username);

        return input.getResponseBuilder()
                .withSpeech(speechtext)
                .withSimpleCard("Spin2Time", speechtext)
                .build();
    }

    private String getProjects(String username){
        List projects;
        String res = "";
        if(cc.userExists(username)){
            projects = getAllProjects(username);
            if(!projects.isEmpty()) {
                res += "Der Benutzer "+username+" ist bei folgenden Projekten Mitglied: ";
                for (Object o : projects) {
                    res += o.toString() + ", ";
                }
                return res;
            }
            else{
                res += "Der Benutzer "+username+" ist bei keinem Projekt Mitglied.";
                return res;
            }
        }
        else{
            res += "Der Benutzer "+username+" existiert leider nicht.";
            return res;
        }
    }
    public List getAllProjects(String username) {
        ConnectionClass connection = new ConnectionClass();
        return connection.getUserProjects(username);
    }
}
