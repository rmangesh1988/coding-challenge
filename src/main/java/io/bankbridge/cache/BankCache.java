package io.bankbridge.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bankbridge.model.BankModelList;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

import java.io.IOException;

/**
 * Singleton bank cache class
 */
public class BankCache {

    private static BankCache bankCache;

    private CacheManager cacheManager;

    private BankCache() {
        initializeCacheManager();
        populateCache();
    }


    private void initializeCacheManager() {
        this.cacheManager = CacheManagerBuilder
                .newCacheManagerBuilder().withCache("banks", CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.heap(10)))
                .build();
        cacheManager.init();
    }

    private void populateCache() {
        Cache cache = getBankCacheFromCacheManager();
        try {
            BankModelList models = new ObjectMapper().readValue(
                    Thread.currentThread().getContextClassLoader().getResource("banks-v1.json"), BankModelList.class);

            models.getBanks().forEach(bank -> {
                cache.put(bank.getBic(), bank.getName());
            });
        } catch (IOException e) {
            throw new RuntimeException("Error during populating cache", e);
        }
    }

    public Cache<String, String> getBankCacheFromCacheManager() {
        return cacheManager.getCache("banks", String.class, String.class);
    }

    public static BankCache getInstance() {
        if(bankCache == null)
            bankCache = new BankCache();
        return bankCache;
    }

}
