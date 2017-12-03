package blservice.billblservice;

import vo.billvo.SaleReturnBillVO;

public interface SaleReturnBillBLService extends BillBLService {
    
    boolean saveBill(SaleReturnBillVO bill);
    
    boolean updateBill(SaleReturnBillVO bill);
    
    SaleReturnBillVO getBill(String id);

}
