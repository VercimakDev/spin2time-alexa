package at.spin2time.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import lombok.extern.log4j.Log4j;

import java.util.Optional;

/**
 * UnknownRequestHandler handles unknown intent requests
 */
@Log4j
public class UnknownRequestHandler implements RequestHandler {

    /**
     * Always returns true, if intent isn't recognized by other intent handlers.
     * @param handlerInput unknown received request
     * @return true (always)
     */
    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return true;
    }


    /**
     * Handles the unrecognized request, shouldEndSession is set to true
     * @param handlerInput unknown request received by alexa
     * @return prompttext
     */
    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        log.error("Unrecognized intent request received");
        log.info(handlerInput.getRequestEnvelopeJson());
        return handlerInput.getResponseBuilder()
                .withSpeech("Tut mir leid, aber das kenne ich nicht. Versuchen Sie einen anderen Befehl.")
                .withSimpleCard("Befehl nicht erkannt","Tut mir leid, das habe ich nicht erkannt.")
                .withShouldEndSession(false)
                .build();
    }

}
