package at.spin2time.util;

import com.amazon.ask.attributes.AttributesManager;
import lombok.extern.log4j.Log4j2;

import java.util.Map;
import java.util.Optional;

@Log4j2
/**
 * Simple class to create Session Attributes
 */
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

    public static <T> T getSessionAttribute(AttributesManager attributesManager, String key, Class<T> c) {
        if (c == null) {
            return null;
        }
        Map<String, Object> alexaSession = attributesManager.getSessionAttributes();
        try {
            T sessionAttribute = c.cast(alexaSession.get(key));
            return sessionAttribute;
        } catch (ClassCastException e) {
            return null;
        }
    }

}
