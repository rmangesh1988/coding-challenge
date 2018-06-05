package io.bankbridge.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

/**
 * Singleton remote config class
 */
public class RemoteConfigImpl implements RemoteConfig<Map<String, String>> {

    private static RemoteConfigImpl remoteConfig;

    private Map<String, String> config;

    private RemoteConfigImpl() {
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

    public static RemoteConfigImpl getInstance() {
        if(remoteConfig == null)
            remoteConfig = new RemoteConfigImpl();
        return remoteConfig;
    }

    public Map<String, String> getConfig() {
        return config;
    }
}
