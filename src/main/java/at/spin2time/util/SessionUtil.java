package at.spin2time.util;

import com.amazon.ask.attributes.AttributesManager;
import lombok.extern.log4j.Log4j;

import java.util.Map;
import java.util.Optional;

@Log4j
public class SessionUtil{

    public static void addOrUpdateSessionAttribute(AttributesManager attributesManager, String key, Object value) {
        Optional.ofNullable(attributesManager)
                .map(AttributesManager::getSessionAttributes)
                .ifPresent((alexaSession) -> {
                    log.info(String.format("Add session attribute %s %s", key, value));
                    alexaSession.put(key, value);
                    attributesManager.setSessionAttributes(alexaSession);
                });
    }

    public static <T> T getSessionAttribute(AttributesManager attributesManager, String key, Class<T> clazz) {
        if (clazz == null) {
            return null;
        }
        Map<String, Object> alexaSession = attributesManager.getSessionAttributes();
        try {
            T sessionAttribute = clazz.cast(alexaSession.get(key));
            return sessionAttribute;
        } catch (ClassCastException e) {
            return null;
        }
    }

}
