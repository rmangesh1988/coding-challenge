package io.bankbridge.client;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Bank api client to call bank services
 */
public class BankApiClient {

    public HttpResponse<JsonNode> getAllBanks(String uri) throws UnirestException {
        return Unirest.get(uri).asJson();
    }

}
