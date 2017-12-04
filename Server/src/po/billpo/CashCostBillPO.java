package po.billpo;

import java.util.ArrayList;

public class CashCostBillPO extends BillPO {

	private String accountId;
	private ArrayList<CashCostItem> cashcostList;
	
	public CashCostBillPO(String date, String time, String id, String operatorId, int state, String accountId, ArrayList<CashCostItem> cashcostList) {
		super(date, time, id, operatorId, state);
		this.accountId = accountId;
		this.cashcostList = cashcostList;
	}
	
	public String getAccountId() {
		return accountId;
	}
	
	public ArrayList<CashCostItem> getCashcostList() {
		return cashcostList;
	}
}
