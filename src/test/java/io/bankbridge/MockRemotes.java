package io.bankbridge;
import spark.Service;

import static spark.Spark.get;
import static spark.Spark.port;

public class MockRemotes {

	public static Service http;

	public static void main(String[] args) throws Exception {
		igniteMockSpark();
	}

	private static void igniteMockSpark() {

		http = Service.ignite().port(1234);

		http.get("/rbb", (request, response) -> "{\n" +
				"\"bic\":\"1234\",\n" +
				"\"countryCode\":\"GB\",\n" +
				"\"auth\":\"OAUTH\"\n" +
				"}");
		http.get("/cs", (request, response) -> "{\n" +
				"\"bic\":\"5678\",\n" +
				"\"countryCode\":\"CH\",\n" +
				"\"auth\":\"OpenID\"\n" +
				"}");
		http.get("/bes", (request, response) -> "{\n" +
				"\"name\":\"Banco de espiritu santo\",\n" +
				"\"countryCode\":\"PT\",\n" +
				"\"auth\":\"SSL\"\n" +
				"}");
	}

	public static void stopMock() {
		if(http != null)
			http.stop();
	}
}