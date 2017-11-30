package vo.billvo;

import presentation.component.MyTableModel;

public class SaleBillVO extends BillVO {

	private String customerId;
	private MyTableModel tableModel;
	
	public SaleBillVO(String date, String time, String id, String operator, int state, String customerId, MyTableModel tableModel) {
		super(date, time, id, operator, state);
		this.customerId = customerId;
		this.tableModel = tableModel;
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
		return "XSD-" + this.getDate() + "-" + this.getId();
	}

}
