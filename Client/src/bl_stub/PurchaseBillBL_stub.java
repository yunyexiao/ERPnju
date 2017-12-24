package bl_stub;

import blservice.billblservice.PurchaseBillBLService;
import presentation.component.MyTableModel;
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
    public MyTableModel getFinishedBills(String customerId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MyTableModel search(String type, String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MyTableModel getBillByDate(String from, String to) {
        // TODO Auto-generated method stub
        return null;
    }

}
