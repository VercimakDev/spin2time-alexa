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
        return input.getResponseBuilder()
                .withSpeech("")
                .withSimpleCard("Spin2Time", "CurrentTime Abfrage erfolgrecih ausgeführt")
                .build();
    }
}
