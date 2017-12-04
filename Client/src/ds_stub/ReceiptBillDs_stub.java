package ds_stub;

import dataservice.ReceiptBillDataService;
import po.billpo.ReceiptBillPO;

public class ReceiptBillDs_stub implements ReceiptBillDataService {
	public ReceiptBillDs_stub() {}

    @Override
    public boolean saveBill(ReceiptBillPO receiptBill) {
        System.out.println("receipt bill saved in database: " + receiptBill.getId());
        return true;
    }

    @Override
    public boolean deleteBill(String id) {
        System.out.println("receipt bill deleted in database: " + id);
        return true;
    }

    @Override
    public String getNewId() {
        return "00123";
    }

    @Override
    public ReceiptBillPO getBillById(String id) {
        return null;
    }

}