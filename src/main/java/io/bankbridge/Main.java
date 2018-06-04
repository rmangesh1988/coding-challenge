package io.bankbridge;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bankbridge.controller.BankController;
import io.bankbridge.exception.BankExceptionHandler;
import io.bankbridge.filter.LoggingFilter;
import io.bankbridge.handler.BanksCacheBased;
import io.bankbridge.handler.BanksRemoteCalls;

import static spark.Spark.*;

public class Main {

	private static final Integer defaultPort = 9001;

	public static void main(String[] args) throws Exception {

		if(args != null && args.length > 0) {
			port(Integer.parseInt(args[0]));
		} else {
			port(defaultPort);
		}

		ObjectMapper objectMapper = new ObjectMapper();
		BanksCacheBased banksCacheBased = new BanksCacheBased();
		BanksRemoteCalls banksRemoteCalls = new BanksRemoteCalls();

		//Logging all incoming requests
		before(LoggingFilter::handle);

		//Load api
		new BankController(objectMapper, banksCacheBased, banksRemoteCalls);

		//Exception handling
		exception(Exception.class, BankExceptionHandler::handle);

	}
}