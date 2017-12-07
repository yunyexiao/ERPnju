package blservice.billblservice;

import vo.billvo.PaymentBillVO;

public interface PaymentBillBLService extends BillBLService {
	
	public boolean saveBill(PaymentBillVO bill);
	
	public boolean updateBill(PaymentBillVO bill);
	/**
	 * ����ὫPOת����VO
	 * @param id ������id���
	 * @return �ֽ���õ��ݵ�VO��
	 */
	public PaymentBillVO getBill(String id);
}
