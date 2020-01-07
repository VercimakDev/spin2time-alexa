package at.spin2time.handlers;

import java.util.Optional;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import lombok.extern.log4j.Log4j2;

import static com.amazon.ask.request.Predicates.requestType;

@Log4j2
public class LaunchRequestHandler extends IntentHandler  {

    @Override
    public boolean canHandle(HandlerInput input) {
        log.info("Request can be handled by LaunchRequestHandler");
        return input.matches(requestType(LaunchRequest.class));
    }

    @Override
    public String getIntentRequestName() {
        return "LaunchRequest";
    }

    @Override
    public Optional<Response> handleWithoutPersInfo() {
        final String speechtext = "Willkommen zu Spin to time!";
        return handlerInput.getResponseBuilder()
                .withShouldEndSession(false)
                .withSpeech(speechtext)
                .withSimpleCard("Spin2Time", "Das moderne Projektmanagementtool.")
                .withReprompt("Sagen Sie Hilfe um verfuegbare Sprachbefehle zu erhalten.")
                .build();
    }

    @Override
    public Optional<Response> handleWithPersInfo(String personId) {
        ConnectionClass cc = new ConnectionClass();
        String username = cc.getUserFromVoiceId(personId);
        if(username != null) {
            final String speechtext = "Willkommen, " + username + ", zu Spin to time!";
            return handlerInput.getResponseBuilder()
                    .withShouldEndSession(false)
                    .withSpeech(speechtext)
                    .withSimpleCard("Spin2Time", "Das moderne Projektmanagementtool.")
                    .withReprompt("Sagen Sie Hilfe um verfuegbare Sprachbefehle zu erhalten.")
                    .build();
        }
        return handleWithoutPersInfo();
    }
}
