package ds_stub;

import dataservice.ReceiptBillDataService;
import po.billpo.ReceiptBillPO;

public class ReceiptBillDs_stub implements ReceiptBillDataService {

	@Override
	public boolean saveBill(ReceiptBillPO bill) {
        System.out.println("receipt bill saved in database: " + bill.getId());
		return true;
	}

	@Override
	public boolean deleteBill(String billid) {
        System.out.println("receipt bill deleted in database: " + billid);
		return true;
	}

	@Override
	public String getNewId() {
		return "00001";
	}

	@Override
	public ReceiptBillPO getBillById(String id) {
		return null;
	}

}
