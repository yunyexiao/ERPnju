package blservice.billblservice;

import vo.billvo.CashCostBillVO;

public interface CashCostBillBLService extends BillCreateBLService {
    boolean updateBill(CashCostBillVO bill);
	
	/**
	 * �����ݱ��浽���ݿ⣨�ͱ��浥�ݲ�vanȫһ�£�
	 * @return �Ƿ񱣴�ɹ�
	 */
	boolean saveBill(CashCostBillVO bill);
}