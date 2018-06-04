package io.bankbridge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bankbridge.handler.BanksCacheBased;
import io.bankbridge.handler.BanksRemoteCalls;

import static spark.Spark.get;

/**
 * Bank controller which has all the Bank resource related services
 */
public class BankController {

    private ObjectMapper objectMapper;

    private BanksCacheBased banksCacheBased;

    private BanksRemoteCalls banksRemoteCalls;

    public BankController(ObjectMapper objectMapper, BanksCacheBased banksCacheBased, BanksRemoteCalls banksRemoteCalls) {
        this.objectMapper = objectMapper;
        this.banksCacheBased = banksCacheBased;
        this.banksRemoteCalls = banksRemoteCalls;
        loadBankApi();
    }

    public void loadBankApi() {
        get("/v1/banks/all", banksCacheBased::handle, objectMapper::writeValueAsString);
        get("/v2/banks/all", banksRemoteCalls::handle, objectMapper::writeValueAsString);
    }
}
