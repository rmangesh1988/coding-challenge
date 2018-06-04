package io.bankbridge.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

/**
 * Centralized exception handling
 */
public class BankExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(BankExceptionHandler.class);

    public static void handle(Exception ex, Request request, Response response) {
        logger.error("Exception in application : ",ex);
        response.status(500);
        response.body("Internal server error");
    }
}
