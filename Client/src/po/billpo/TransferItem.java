package po.billpo;

public class TransferItem {

	private String accountId;
	private double money;
	private String remark;
	
	public TransferItem(String accountId, double money, String remark) {
		this.accountId = accountId;
		this.money = money;
		this.remark = remark;
	}
	
	public String getAccountId() {
		return this.accountId;
	}

	public double getMoney() {
		return this.money;
	}

	public String getRemark() {
		return this.remark;
	}
}
