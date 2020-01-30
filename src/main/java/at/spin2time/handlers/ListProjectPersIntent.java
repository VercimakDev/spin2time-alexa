package at.spin2time.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

import java.util.List;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class ListProjectPersIntent extends IntentHandler {
    ConnectionClass cc = new ConnectionClass();
    public boolean canHandle(HandlerInput input, IntentRequest intentRequest) {
        return input.matches(intentName("ListProjectPersIntent"));
    }
    @Override
    public String getIntentRequestName() {
        return "ListProjectPersIntent";
    }

    @Override
    public Optional<Response> handleWithoutPersInfo() {

        return handlerInput.getResponseBuilder()
                .withShouldEndSession(false)
                .withSpeech("Um diese Funktion zu nutzen, muessen Sie einen Nutzernamen angeben oder ein Voice-Profil erstellen.")
                .withReprompt("Versuchen Sie beispielsweise: 'Hannes arbeitet jetzt'")
                .build();

    }

    @Override
    public Optional<Response> handleWithPersInfo(String personId) {
        String speechtext = getProjects(personId);

        return handlerInput.getResponseBuilder()
                .withSpeech(speechtext)
                .withSimpleCard("Spin2Time", speechtext)
                .build();
    }
    private String getProjects(String personId){
        String username = cc.getUserFromVoiceId(personId);
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
