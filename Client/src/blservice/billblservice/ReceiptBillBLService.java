package blservice.billblservice;

import vo.billvo.ReceiptBillVO;

public interface ReceiptBillBLService extends BillBLService {

	public boolean saveBill(ReceiptBillVO bill);
	
	public boolean updateBill(ReceiptBillVO bill);
	/**
	 * ����ὫPOת����VO
	 * @param id ������id���
	 * @return �ֽ���õ��ݵ�VO��
	 */
	public ReceiptBillVO getBill(String id);
}
