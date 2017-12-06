package po.billpo;

import java.util.ArrayList;

public class CashCostBillPO extends BillPO {

	

	private String accountId;
	private ArrayList<CashCostItem> cashcostList;
	private double sum;
	
	public CashCostBillPO(String date, String time, String id, String operatorId, int state, String accountId,
			ArrayList<CashCostItem> cashcostList, double sum) {
		super(date, time, id, operatorId, state);
		this.accountId = accountId;
		this.cashcostList = cashcostList;
		this.sum = sum;
	}
	
	public String getAccountId() {
		return accountId;
	}
	
	public ArrayList<CashCostItem> getCashcostList() {
		return cashcostList;
	}

	public double getSum() {
		return sum;
	}
}
