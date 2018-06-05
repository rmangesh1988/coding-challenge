package io.bankbridge.handler;

import io.bankbridge.cache.BankCache;
import io.bankbridge.vo.BankVO;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import spark.Request;
import spark.Response;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Handle test for banks cache based
 */
@RunWith(MockitoJUnitRunner.class)
public class BanksCacheBasedTest {

    @Mock
    private BankCache bankCache;

    @Mock
    private Request request;

    @Mock
    private Response response;

    private BanksCacheBased banksCacheBased;

    private static Cache<String, String> cache;

    @BeforeClass
    public static void initCache() {
        cache = populateDummyCache();
    }

    @Before
    public void setup() {
        banksCacheBased = new BanksCacheBased(bankCache);
    }

    @Test
    public void testBanksCacheBasedHandleReturnsBanksCorrectly() {
        //Expected
        List<BankVO> expectedBanks = populateBanks();

        //When
        when(bankCache.getBanks()).thenReturn(cache);

        //Then
        List<BankVO> actualBanks = banksCacheBased.handle(request, response);

        //Result
        assertEquals("Number of banks", expectedBanks.size(), actualBanks.size());
        assertThat(actualBanks).containsExactlyInAnyOrder(expectedBanks.toArray(new BankVO[expectedBanks.size()]));
        verify(bankCache, times(1)).getBanks();
        verifyNoMoreInteractions(bankCache);
    }

    private static Cache<String, String> populateDummyCache() {
        CacheManager cacheManager = CacheManagerBuilder
                .newCacheManagerBuilder().withCache("banks", CacheConfigurationBuilder
                        .newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.heap(10)))
                .build();
        cacheManager.init();

        Cache cache = cacheManager.getCache("banks", String.class, String.class);

        cache.put("1", "bank1");
        cache.put("2", "bank2");
        cache.put("3", "bank3");

        return cache;
    }

    private List<BankVO> populateBanks() {
        return Arrays.asList(
                new BankVO("1", "bank1"),
                new BankVO("2", "bank2"),
                new BankVO("3", "bank3")
        );
    }

}
