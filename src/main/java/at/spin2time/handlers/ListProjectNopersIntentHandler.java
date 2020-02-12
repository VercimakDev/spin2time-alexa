package at.spin2time.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazon.ask.request.exception.handler.impl.AbstractHandlerInput;

import java.util.List;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class ListProjectNopersIntentHandler extends IntentHandler {

    ConnectionClass cc = new ConnectionClass();

    public boolean canHandle(HandlerInput input, IntentRequest intentRequest) {
        return input.matches(intentName("ListProjectNopersIntent"));
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

    @Override
    public String getIntentRequestName() {
        return "ListProjectNopersIntent";
    }

    @Override
    public Optional<Response> handleWithoutPersInfo() {
        String username = getUsernameFromRequest();
        String speechtext = getProjects(username);
        return handlerInput.getResponseBuilder()
                .withSpeech(speechtext)
                .withSimpleCard("Spin2Time", speechtext)
                .build();
    }

    @Override
    public Optional<Response> handleWithPersInfo(String personId) {
        String username = getUsernameFromRequest();
        String speechtext = getProjects(username);
        return handlerInput.getResponseBuilder()
                .withSpeech(speechtext)
                .withSimpleCard("Spin2Time", speechtext)
                .build();
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
