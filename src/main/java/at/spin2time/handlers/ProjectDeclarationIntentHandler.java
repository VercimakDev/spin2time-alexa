package at.spin2time.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class ProjectDeclarationIntentHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(intentName("ProjectDeclarationIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        String speechtext = "Project Declaration Intent erfolgreich aufgerufen!";
        return handlerInput.getResponseBuilder()
                .withSpeech(speechtext)
                .withSimpleCard("Spin2Time",speechtext)
                .build();
    }
}
