package blservice.billblservice;

import vo.billvo.CashCostBillVO;

public interface CashCostBillBLService extends BillCreateBLService {
	/**
	 * �����ݱ��浽���ݿ�
	 * @return �Ƿ񱣴�ɹ�
	 */
	boolean saveBill(CashCostBillVO bill);
}