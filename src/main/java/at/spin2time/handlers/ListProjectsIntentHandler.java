package at.spin2time.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class ListProjectsIntentHandler implements IntentRequestHandler {
    @Override
    public boolean canHandle(HandlerInput input, IntentRequest intentRequest) {
        return input.matches(intentName("ListProjectsIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input, IntentRequest intentRequest) {

        Intent intent = intentRequest.getIntent();

        String username = intent.getSlots().get("name").getValue();
        
        return input.getResponseBuilder()
                .withSpeech("ListProjectsIntent aufgerufen")
                .withSimpleCard("Spin2Time", "ListProjectsIntent")
                .build();
    }

}
