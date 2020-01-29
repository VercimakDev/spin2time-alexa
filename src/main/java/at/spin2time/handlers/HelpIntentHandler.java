package at.spin2time.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class HelpIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(intentName("AMAZON.HelpIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        String speechtext = "Spin2Time hilft Ihnen Ihre Arbeitszeiten einfach und schnell einzutragen," +
                "Sie können folgende Befehle benutzen: Zeitaufzeichnung starten oder stoppen," +
                " Ihre aufgezeichnete Zeit abfragen und vieles mehr";
        return handlerInput.getResponseBuilder()
                .withSpeech(speechtext)
                .withSimpleCard("Spin2Time", speechtext)
                .withReprompt("Probieren sie mal zu Sagen, Alexa wie lange hat Benutzername diesen Monat gearbeitet.")
                .build();
    }
}
