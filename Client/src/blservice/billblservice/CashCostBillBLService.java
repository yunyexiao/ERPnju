package blservice.billblservice;

import vo.billvo.CashCostBillVO;

public interface CashCostBillBLService extends BillBLService {
	
	/**
	 * �����ݱ��浽���ݿ⣨�ͱ��浥�ݲ�vanȫһ�£�
	 * @return �Ƿ񱣴�ɹ�
	 */
	public boolean saveBill(CashCostBillVO bill);
	/**
	 * ����ὫPOת����VO
	 * @param id ������id���
	 * @return �ֽ���õ��ݵ�VO��
	 */
	public CashCostBillVO getBill(String id);

}