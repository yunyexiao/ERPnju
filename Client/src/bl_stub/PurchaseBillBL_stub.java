package bl_stub;

import blservice.billblservice.SaleBillBLService;
import vo.billvo.SaleBillVO;


public class PurchaseBillBL_stub implements SaleBillBLService {

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
    public boolean saveBill(SaleBillVO bill) {
        System.out.println(bill.getId() + " has been saved");
        return true;
    }

    @Override
    public boolean updateBill(SaleBillVO bill) {
        System.out.println(bill.getId() + " has been updated");
        return true;
    }

    @Override
    public SaleBillVO getBill(String id) {
        // TODO Auto-generated method stub
        return null;
    }

}
