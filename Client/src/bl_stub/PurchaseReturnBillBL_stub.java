package bl_stub;

import blservice.billblservice.PurchaseReturnBillBLService;
import vo.billvo.PurchaseReturnBillVO;


public class PurchaseReturnBillBL_stub implements PurchaseReturnBillBLService {

    public PurchaseReturnBillBL_stub() {}

    @Override
    public String getNewId() {
        return "JHTHD-20171203-02200";
    }

    @Override
    public boolean deleteBill(String id) {
        System.out.println("purchase return bill deleted: " + id);
        return true; 
    }

    @Override
    public boolean saveBill(PurchaseReturnBillVO bill) {
        System.out.println("purchase return bill saved: " + bill.getId());
        return true;
    }

    @Override
    public boolean updateBill(PurchaseReturnBillVO bill) {
        System.out.println("purchase return bill updated: " + bill.getId());
        return true;
    }
}
