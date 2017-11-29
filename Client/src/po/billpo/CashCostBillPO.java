package po.billpo;

import java.util.ArrayList;

public class CashCostBillPO extends BillPO {

	private String accountId;
	private ArrayList<CashCostItem> cashcostList;
	
	public CashCostBillPO(String date, String time, String id, String operator, int state, String accountId, ArrayList<CashCostItem> cashcostList) {
		super(date, time, id, operator, state);
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
