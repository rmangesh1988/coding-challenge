package io.bankbridge.handler;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.bankbridge.client.BankApiClient;
import io.bankbridge.config.RemoteConfig;
import io.bankbridge.vo.BankVO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import spark.Request;
import spark.Response;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Handle test for banks remote calls
 */
@RunWith(MockitoJUnitRunner.class)
public class BanksRemoteCallsTest {

    @Mock
    private BankApiClient bankApiClient;

    @Mock
    private Request request;

    @Mock
    private Response response;

    @Mock
    private RemoteConfig config;

    private BanksRemoteCalls banksRemoteCalls;

    @Before
    public void setup() {
        banksRemoteCalls = new BanksRemoteCalls(config, bankApiClient);
    }

    @Test
    public void testBanksRemoteCallHandleReturnsBanksCorrectly() throws UnirestException {
        //Expected
        List<BankVO> expectedBanks = populateBanks();

        //When
        Map<String, String> testConfig = new HashMap<>();
        testConfig.put("bank1","http://localhost:2222/bank1");

        when(config.getConfig()).thenReturn(testConfig);
        HttpResponse<JsonNode> httpResponse = mock(HttpResponse.class); 
        when(bankApiClient.getAllBanks(testConfig.get("bank1"))).thenReturn(httpResponse);
        JsonNode jsonNode = mock(JsonNode.class);
        when(httpResponse.getBody()).thenReturn(jsonNode);
        when(jsonNode.toString()).thenReturn(resultJsonString());

        //Then
        List<BankVO> actualBanks = banksRemoteCalls.handle(request, response);

        //Result
        assertEquals("Number of banks", expectedBanks.size(), actualBanks.size());
        assertThat(actualBanks).isEqualTo(expectedBanks);
        verify(bankApiClient, times(1)).getAllBanks(anyString());
        verifyNoMoreInteractions(bankApiClient);
    }

    private String resultJsonString() {
        return "{\n" +
                "\"bic\":\"1\",\n" +
                "\"name\":\"bank1\",\n" +
                "\"auth\":\"OAUTH\"\n" +
                "}";
    }

    private List<BankVO> populateBanks() {
        return Arrays.asList(
                new BankVO("1", "bank1")
        );
    }
}
