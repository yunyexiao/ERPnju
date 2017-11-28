package vo.billvo;

import presentation.component.MyTableModel;

public class SaleBillVO extends BillVO {

	private String customerId;
	private MyTableModel tableModel;
	
	public SaleBillVO(String date, String time, String id, String operator, int state, String customerId) {
		super(date, time, id, operator, state);
		this.customerId = customerId;
	}

	public String getCustomerId() {
		return customerId;
	}
	
	public MyTableModel getTableModel() {
		return tableModel;
	}
	
	@Override
	public String getAllId() {
		return "XSD-" + this.getDate() + "-" + this.getId();
	}

}