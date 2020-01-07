package at.spin2time.handlers;

import com.amazon.ask.dispatcher.exception.ExceptionHandler;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;

import java.util.Optional;

/**
 * This handler gets activated if an exception occurs while the Spin2Time skill processes a request
 */
public class GenericExceptionHandler implements ExceptionHandler {

    /**
     * Returns true if an exception occurs in the skill
     * @param handlerInput
     * @param throwable
     * @return true if exception occurs
     */
    @Override
    public boolean canHandle(HandlerInput handlerInput, Throwable throwable) {
        return true;
    }

    /**
     * Handles the request
     * @param handlerInput request from alexa
     * @param throwable exception thrown by skill
     * @return response from alexa
     */
    @Override
    public Optional<Response> handle(HandlerInput handlerInput, Throwable throwable) {
        String errorMessage = throwable.getMessage();
        return handlerInput.getResponseBuilder()
                .withSpeech(errorMessage)
                .withShouldEndSession(true)
                .build();
    }

}
