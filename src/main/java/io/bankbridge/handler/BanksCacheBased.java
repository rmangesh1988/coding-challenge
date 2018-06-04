package io.bankbridge.handler;
import java.util.ArrayList;
import java.util.List;

import io.bankbridge.cache.BankCache;
import io.bankbridge.vo.BankVO;
import spark.Request;
import spark.Response;

public class BanksCacheBased {

	private BankCache bankCache;

	public BanksCacheBased() {
		bankCache = BankCache.getInstance();
	}

	public BanksCacheBased(BankCache bankCache) {
		this.bankCache = bankCache;
	}

	//Handle method returning list of BankVOs from cache
	public List<BankVO> handle(Request request, Response response) {
		List<BankVO> banks = new ArrayList<>();

		bankCache.getBankCacheFromCacheManager().forEach(entry -> {
			banks.add(new BankVO(entry.getKey(), entry.getValue()));
		});

		return banks;
	}

}
