package io.bankbridge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.bankbridge.client.BankApiClient;
import io.bankbridge.vo.BankVO;
import org.apache.http.HttpStatus;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static io.bankbridge.MockRemotes.stopMock;
import static org.junit.Assert.assertEquals;
import static spark.Spark.stop;

/**
 * Bank api tests - v1,v2
 */
public class BankAPIIntegrationTest {

    private static final String testPort = "4567";

    private static final String testServer = "http://localhost";

    private static BankApiClient bankApiClient;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeClass
    public static void startServer() throws Exception {
        bankApiClient = new BankApiClient();
        Main.main(new String[] {testPort});
        MockRemotes.main(null);
    }

    @Test
    public void testAllBanksReturnedFromV1Service() throws UnirestException, IOException {
        HttpResponse<JsonNode> response = bankApiClient.getAllBanks(testServer+":"+testPort+"/v1/banks/all");

        String jsonString = response.getBody().toString();
        List<BankVO> banks = objectMapper.readValue(jsonString, List.class);

        assertEquals("Http status", HttpStatus.SC_OK, response.getStatus());
        assertEquals("Number of banks", 3, banks.size());

    }

    @Test
    public void testAllBanksReturnedFromV2Service() throws UnirestException, IOException {
        HttpResponse<JsonNode> response = bankApiClient.getAllBanks(testServer+":"+testPort+"/v2/banks/all");

        String jsonString = response.getBody().toString();
        List<BankVO> banks = objectMapper.readValue(jsonString, List.class);

        assertEquals("Http status", HttpStatus.SC_OK, response.getStatus());
        assertEquals("Number of banks", 3, banks.size());

    }

    @AfterClass
    public static void stopServer() {
        stop();
        stopMock();
    }
}
