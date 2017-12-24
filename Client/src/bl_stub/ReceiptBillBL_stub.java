package bl_stub;

import blservice.billblservice.ReceiptBillBLService;
import vo.billvo.ReceiptBillVO;


public class ReceiptBillBL_stub implements ReceiptBillBLService {

    public ReceiptBillBL_stub() {}

    @Override
    public String getNewId() {
        return "SKD-20171204-00001";
    }

    @Override
    public boolean deleteBill(String id) {
        System.out.println(id + " has been deleted");
        return true;
    }

    @Override
    public boolean saveBill(ReceiptBillVO bill) {
        System.out.println(bill.getId() + " has been saved");
        return true;
    }

    @Override
    public boolean updateBill(ReceiptBillVO bill) {
        System.out.println(bill.getId() + " has been updated");
        return true;
    }
}