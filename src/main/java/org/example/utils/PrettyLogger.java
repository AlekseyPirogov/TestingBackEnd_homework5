package org.example.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.internal.platform.Platform;
import okhttp3.logging.HttpLoggingInterceptor;

public class PrettyLogger implements HttpLoggingInterceptor.Logger {
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public void log(String message) {
        String tirimmedMessage = message.trim();
        if ((tirimmedMessage.startsWith("{")) && tirimmedMessage.endsWith("}")
            || (tirimmedMessage.startsWith("[")) && tirimmedMessage.endsWith("]")) {
            try {
                Object value = mapper.readValue(message, Object.class);
                String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
                Platform.get().log(Platform.INFO, prettyJson, null);
            } catch (JsonProcessingException e) {
                Platform.get().log(Platform.WARN, message, e);
            }
        } else {
            Platform.get().log(Platform.INFO, message, null);
        }
    }
}
