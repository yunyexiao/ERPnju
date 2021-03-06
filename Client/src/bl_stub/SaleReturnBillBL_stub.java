package bl_stub;

import blservice.billblservice.SalesReturnBillBLService;
import vo.billvo.SalesReturnBillVO;


public class SaleReturnBillBL_stub implements SalesReturnBillBLService {

    public SaleReturnBillBL_stub() {}

    @Override
    public String getNewId() {
        return "00112";
    }

    @Override
    public boolean deleteBill(String id) {
        System.out.println("sale return bill deleted: " + id);
        return true;
    }

    @Override
    public boolean saveBill(SalesReturnBillVO bill) {
        System.out.println("sale return bill saved: " + bill.getAllId());
        return true;
    }

    @Override
    public boolean updateBill(SalesReturnBillVO bill) {
        System.out.println("sale return bill updated: " + bill.getAllId());
        return true;
    }
}
