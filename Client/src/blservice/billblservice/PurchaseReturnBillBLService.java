package blservice.billblservice;

import vo.billvo.PurchaseReturnBillVO;

public interface PurchaseReturnBillBLService extends BillBLService {
    
    boolean saveBill(PurchaseReturnBillVO bill);
    
    boolean updateBill(PurchaseReturnBillVO bill);
    
    PurchaseReturnBillVO getBill(String id);

}
