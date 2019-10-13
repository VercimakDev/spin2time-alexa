package at.spin2time.handlers;

import java.util.Optional;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import static com.amazon.ask.request.Predicates.requestType;

public class LaunchRequestHandler implements RequestHandler  {

    public boolean canHandle(HandlerInput input) {
        return input.matches(requestType(LaunchRequest.class));
    }

    public Optional<Response> handle(HandlerInput input) {
        String speechText = "Willkommen zu Spin to time. Benutzen Sie Hilfe um einige verfügbare Sprachbefehle zu bekommen.";
        return input.getResponseBuilder()
                .withShouldEndSession(false)
                .withSpeech(speechText)
                .withSimpleCard("Spin2Time", speechText)
                .withReprompt(speechText)
                .build();
    }

}
