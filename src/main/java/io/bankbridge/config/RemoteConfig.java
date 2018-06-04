package io.bankbridge.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

/**
 * Singleton remote config class
 */
public class RemoteConfig {

    private static RemoteConfig remoteConfig;

    private Map<String, String> config;

    private RemoteConfig() {
        loadConfig();
    }

    private void loadConfig() {
        try {
            config = new ObjectMapper()
                    .readValue(Thread.currentThread().getContextClassLoader().getResource("banks-v2.json"), Map.class);
        } catch (IOException e) {
            throw new RuntimeException("Error during fetching config!", e);
        }
    }

    public static RemoteConfig getInstance() {
        if(remoteConfig == null)
            remoteConfig = new RemoteConfig();
        return remoteConfig;
    }

    public Map<String, String> getConfig() {
        return config;
    }
}
