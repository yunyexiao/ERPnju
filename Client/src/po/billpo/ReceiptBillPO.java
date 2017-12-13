package po.billpo;

import java.io.Serializable;
import java.util.ArrayList;

public class ReceiptBillPO extends BillPO implements Serializable{

	private String customerId;
	private ArrayList<TransferItem> transferList;
	
	public ReceiptBillPO(String date, String time, String id, String operator, int state, String customerId, ArrayList<TransferItem> transferList) {
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

	@Override
	public String getAllId() {
		return "SKD-" + this.getDate() + "-" + this.getId();
	}
	
}
