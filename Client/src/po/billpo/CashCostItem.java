package po.billpo;

public class CashCostItem {

	private String name;
	private double money;
	private String remark;
	
	public CashCostItem(String name, double money, String remark) {
		this.name = name;
		this.money = money;
		this.remark = remark;
	}

	public String getName() {
		return this.name;
	}

	public double getMoney() {
		return this.money;
	}

	public String getRemark() {
		return this.remark;
	}
}
