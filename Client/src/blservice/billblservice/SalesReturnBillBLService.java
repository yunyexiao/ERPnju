package blservice.billblservice;

import vo.billvo.SalesReturnBillVO;

public interface SalesReturnBillBLService extends BillBLService {
    
    boolean saveBill(SalesReturnBillVO bill);
    
    boolean updateBill(SalesReturnBillVO bill);
    
    SalesReturnBillVO getBill(String id);

}
