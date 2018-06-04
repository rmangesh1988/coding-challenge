package io.bankbridge.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

/**
 * A logging filter to log all incoming requests
 */
public class LoggingFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    public static void handle(Request request, Response response) {
        logger.info("Incoming request of {} for {} from ip {} ",request.requestMethod(), request.uri(), request.ip());
    }
}
