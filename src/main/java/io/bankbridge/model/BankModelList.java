package io.bankbridge.model;
import java.util.List;
import java.util.Objects;

/**
 * BankList model with proper encapsulation
 */
public class BankModelList {
	
	private List<BankModel> banks;

	public List<BankModel> getBanks() {
		return banks;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BankModelList that = (BankModelList) o;
		return Objects.equals(banks, that.banks);
	}

	@Override
	public int hashCode() {
		return Objects.hash(banks);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("BankModelList{");
		sb.append("banks=").append(banks);
		sb.append('}');
		return sb.toString();
	}
}
