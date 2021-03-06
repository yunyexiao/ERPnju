package vo.billvo;

import presentation.component.MyTableModel;

public class PaymentBillVO extends BillVO {

	private String customerId;
	private MyTableModel tableModel;
	
	public PaymentBillVO(String date, String time, String id, String operator, int state, String customerId) {
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
		return "FKD-" + this.getDate() + "-" + this.getId();
	}

}
