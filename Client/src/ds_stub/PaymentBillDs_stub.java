package ds_stub;

import dataservice.PaymentBillDataService;
import po.billpo.PaymentBillPO;

public class PaymentBillDs_stub implements PaymentBillDataService {

	@Override
	public boolean saveBill(PaymentBillPO bill) {
        System.out.println("payment bill saved in database: " + bill.getId());
		return true;
	}

	@Override
	public boolean deleteBill(String billid) {
        System.out.println("payment bill deleted in database: " + billid);
		return true;
	}

	@Override
	public String getNewId() {
		return "00002";
	}

	@Override
	public PaymentBillPO getBillById(String id) {
		return null;
	}

}
