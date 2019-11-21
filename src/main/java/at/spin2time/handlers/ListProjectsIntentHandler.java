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

        String res = "";
        if(cc.userExists(username)){
            String userid = cc.selectQueryBuilder("select u_id from u_users where u_username = "+username).get(0).toString();
            List projectids = cc.selectQueryBuilder("select pm_p_id pm_projectmembers where pm_u_id = "+userid);

            String ids = "";
            for(Object o : projectids){
                ids += o.toString()+" ";
            }

        }


        return res;
    }

}
