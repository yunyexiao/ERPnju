package blservice.billblservice;

import vo.billvo.BillVO;

/**
 * ������Աʹ�õĽӿ�
 * @author Ǯ��Ե
 *
 */
public interface BillOperationService {

	/**
	 * ��嵥��
	 * @param billId ���ݱ��
	 * @return
	 */
	public boolean offsetBill(String billId);
	/**
	 * ����һ�ŵ���
	 * @param billId ���ݱ��
	 * @return
	 */
	public boolean copyBill(BillVO bill);
}
