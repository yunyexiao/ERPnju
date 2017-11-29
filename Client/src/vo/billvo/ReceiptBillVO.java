package vo.billvo;

import presentation.component.MyTableModel;

public class ReceiptBillVO extends BillVO {

	private String customerId;
	private MyTableModel tableModel;
	
	public ReceiptBillVO(String date, String time, String id, String operator, int state, String customerId) {
		super(date, time, id, operator, state);
		this.customerId = customerId;
	}

	public String getCustomerId() {
		return customerId;
	}
	
	public void setTableModel(MyTableModel tableModel) {
		this.tableModel = tableModel;
	}
	
	public MyTableModel getTableModel() {
		return tableModel;
	}
	
	@Override
	public String getAllId() {
		return "SKD-" + this.getDate() + "-" + this.getId();
	}

}
