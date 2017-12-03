package bl_stub;

import blservice.billblservice.PurchaseBillBLService;
import vo.billvo.MarketBillVO;
import vo.billvo.PurchaseBillVO;


public class PurchaseBillBL_stub implements PurchaseBillBLService {

    public PurchaseBillBL_stub() {}

    @Override
    public String getNewId() {
        return "JHD-20171201-00001";
    }

    @Override
    public boolean deleteBill(String id) {
        System.out.println(id + " has been deleted");
        return true;
    }

    @Override
    public boolean saveBill(PurchaseBillVO bill) {
        System.out.println(bill.getId() + " has been saved");
        return true;
    }

    @Override
    public boolean updateBill(PurchaseBillVO bill) {
        System.out.println(bill.getId() + " has been updated");
        return true;
    }

    @Override
    public PurchaseBillVO getBill(String id) {
        // TODO Auto-generated method stub
        return null;
    }

}
