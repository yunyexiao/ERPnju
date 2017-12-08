package blservice.billblservice;

import presentation.component.MyTableModel;
import vo.billvo.PurchaseBillVO;

public interface PurchaseBillBLService extends BillBLService {
    
    boolean saveBill(PurchaseBillVO bill);
    
    boolean updateBill(PurchaseBillVO bill);
    
    PurchaseBillVO getBill(String id);
    
    MyTableModel getFinishedBills(String customerId);
    
    MyTableModel search(String type, String key);
    
    MyTableModel getBillByDate(String from, String to);

}
