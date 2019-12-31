package at.spin2time.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

/**
 * Handler for AMAZON.StopIntent
 */
public class StopIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(intentName("AMAZON.StopIntent"));
    }


    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        return Optional.empty();
    }

}
