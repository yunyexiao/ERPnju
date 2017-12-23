package blservice.billblservice;

import vo.billvo.BillVO;

/**
 * ���ݲ���ʹ�õĽӿ�
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
	/**
	 * ɾ��һ�ŵ��ݣ����ύ״̬�ĵ��ݲ���ɾ����
	 * @param billId ���ݱ��
	 * @return �Ƿ�ɾ���ɹ�
	 */
	public boolean deleteBill(String billId);
}
