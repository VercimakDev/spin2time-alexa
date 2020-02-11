package at.spin2time.handlers;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import com.amazon.ask.request.Predicates;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;

@Log4j2
/**
 * BaseHandler implements the RequestHandler class from AMAZON
 */
public abstract class BaseHandler implements RequestHandler {

        protected HandlerInput handlerInput;

        protected AttributesManager attributesManager;

        public abstract String getRequestName();

        @Override
        public boolean canHandle(final HandlerInput handlerInput){
            boolean canHandleRequest = handlerInput.matches(Predicates.intentName(getRequestName()));
            final String requestType = Optional.ofNullable(handlerInput)
                    .map(HandlerInput::getRequestEnvelope)
                    .map(RequestEnvelope::getRequest)
                    .map(Request::getType)
                    .orElse("unknownType");
            log.info(String.format("%s handler canHandle returns %s for %s", this.getClass().getSimpleName(),canHandleRequest, requestType));
            return canHandleRequest;
        }

        @Override
        public Optional<Response> handle(final HandlerInput handlerInput){
            try{
                this.handlerInput = handlerInput;
                this.attributesManager = handlerInput.getAttributesManager();
                log.info(handlerInput.getRequestEnvelopeJson());
                return handle();
            }
            catch(Exception e){
                log.error("Exception thrown while processing the request", e);
                throw e;
            }
        }


        /**
     * Final handle method for processing the input and handling it
     * @return Alexa response
     */
    public abstract Optional<Response> handle();

}
