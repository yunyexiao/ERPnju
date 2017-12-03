package bl_stub;

import blservice.billblservice.SaleReturnBillBLService;
import vo.billvo.SaleReturnBillVO;


public class SaleReturnBillBL_stub implements SaleReturnBillBLService {

    public SaleReturnBillBL_stub() {}

    @Override
    public String getNewId() {
        return "XSTHD-20171203-00112";
    }

    @Override
    public boolean deleteBill(String id) {
        System.out.println("sale return bill deleted: " + id);
        return true;
    }

    @Override
    public boolean saveBill(SaleReturnBillVO bill) {
        System.out.println("sale return bill saved: " + bill.getAllId());
        return true;
    }

    @Override
    public boolean updateBill(SaleReturnBillVO bill) {
        System.out.println("sale return bill updated: " + bill.getAllId());
        return true;
    }

    @Override
    public SaleReturnBillVO getBill(String id) {
        return null;
    }

}
