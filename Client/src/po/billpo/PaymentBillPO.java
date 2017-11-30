package po.billpo;

import java.util.ArrayList;

public class PaymentBillPO extends BillPO {

	private String customerId;
	private ArrayList<TransferItem> transferList;
	
	public PaymentBillPO(String date, String time, String id, String operator, int state, String customerId, ArrayList<TransferItem> transferList) {
		super(date, time, id, operator, state);
		this.customerId = customerId;
		this.transferList = transferList;
	}
	
	public String getCustomerId() {
		return customerId;
	}
	
	public ArrayList<TransferItem> getTransferList() {
		return transferList;
	}
	
}
