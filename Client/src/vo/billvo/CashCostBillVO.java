package vo.billvo;

import presentation.component.MyTableModel;

public class CashCostBillVO extends BillVO {

	private String accountId;
	private MyTableModel tableModel;
	
	public CashCostBillVO(String date, String time, String id, String operator, int state, String accountId) {
		super(date, time, id, operator, state);
		this.accountId  = accountId;
	}
	
	public String getAccountId() {
		return accountId;
	}
	
	public void setTableModel(MyTableModel tableModel) {
		this.tableModel = tableModel;
	}
	
	public MyTableModel getTableModel() {
		return tableModel;
	}
	
	@Override
	public String getAllId() {
		return "XJFYD-" + this.getDate() + "-" + this.getId();
	}

}
