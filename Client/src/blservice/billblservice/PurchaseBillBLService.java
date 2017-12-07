package blservice.billblservice;

import vo.billvo.PurchaseBillVO;

public interface PurchaseBillBLService extends BillBLService {
    
    boolean saveBill(PurchaseBillVO bill);
    
    boolean updateBill(PurchaseBillVO bill);
    
    PurchaseBillVO getBill(String id);

}
