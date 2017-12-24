package blservice.billblservice;

import vo.billvo.CashCostBillVO;

public interface CashCostBillBLService extends BillCreateBLService {
    boolean updateBill(CashCostBillVO bill);
	
	/**
	 * 将单据保存到数据库（和保存单据不van全一致）
	 * @return 是否保存成功
	 */
	boolean saveBill(CashCostBillVO bill);
}