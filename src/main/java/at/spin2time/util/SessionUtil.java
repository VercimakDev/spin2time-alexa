package at.spin2time.util;

import com.amazon.ask.attributes.AttributesManager;
import java.util.Map;

public class SessionUtil{

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
