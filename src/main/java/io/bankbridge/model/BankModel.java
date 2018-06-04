package io.bankbridge.model;

import java.util.Objects;

/**
 * Bank model with proper encapsulation
 */
public class BankModel {
	
	private String bic;
	private String name;
	private String countryCode;
	private String auth;

	public String getBic() {
		return bic;
	}

	public String getName() {
		return name;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public String getAuth() {
		return auth;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BankModel bankModel = (BankModel) o;
		return Objects.equals(bic, bankModel.bic) &&
				Objects.equals(name, bankModel.name) &&
				Objects.equals(countryCode, bankModel.countryCode) &&
				Objects.equals(auth, bankModel.auth);
	}

	@Override
	public int hashCode() {
		return Objects.hash(bic, name, countryCode, auth);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("BankModel{");
		sb.append("bic='").append(bic).append('\'');
		sb.append(", name='").append(name).append('\'');
		sb.append(", countryCode='").append(countryCode).append('\'');
		sb.append(", auth='").append(auth).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
