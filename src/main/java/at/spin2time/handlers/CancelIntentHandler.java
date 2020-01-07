package at.spin2time.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;


/**
 * Handler for AMAZON.CancelIntent request
 */
public class CancelIntentHandler extends IntentHandler {


    @Override
    public String getIntentRequestName() {
        return "AMAZON.CancelIntent";
    }

    @Override
    public Optional<Response> handleWithoutPersInfo() {
        return handlerInput.getResponseBuilder()
                .withSpeech("Auf Wiedersehen!")
                .withSimpleCard("Spin2Time","Skill beendet.")
                .withShouldEndSession(true)
                .build();
    }

    @Override
    public Optional<Response> handleWithPersInfo(String personId) {
        ConnectionClass cc = new ConnectionClass();
        String username = cc.getUserFromVoiceId(personId);
        return handlerInput.getResponseBuilder()
                .withSpeech("Auf Wiedersehen "+username+"!")
                .withShouldEndSession(true)
                .build();
    }


}
