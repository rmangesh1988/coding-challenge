package io.bankbridge.cache;

import org.ehcache.Cache;

/**
 * Interface for bank cache
 */
public interface BankCache<U,V> {

    Cache<U,V> getBanks();
}
