package io.bankbridge.handler;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.bankbridge.client.BankApiClient;
import io.bankbridge.config.RemoteConfig;
import io.bankbridge.model.BankModel;
import io.bankbridge.vo.BankVO;
import spark.Request;
import spark.Response;

import static io.bankbridge.helper.ApiHelper.propagateExceptionSoftly;

public class BanksRemoteCalls {

	private RemoteConfig config;

	private BankApiClient bankApiClient;

	public BanksRemoteCalls() {
		bankApiClient = new BankApiClient();
		config = RemoteConfig.getInstance();
	}

	public BanksRemoteCalls(RemoteConfig config, BankApiClient bankApiClient) {
		this.config = config;
		this.bankApiClient = bankApiClient;
	}

	//Handle method returning list of BankVOs from remote calls
	public List<BankVO> handle(Request request, Response response) {
		return config.getConfig().values().stream()
				.map(url -> propagateExceptionSoftly(() -> bankApiClient.getAllBanks(url).getBody().toString()))
				.map(jsonString -> propagateExceptionSoftly(() -> new ObjectMapper().readValue(jsonString, BankModel.class)))
				.map(bm -> new BankVO(bm.getBic(), bm.getName())).collect(Collectors.toList());
	}

}
