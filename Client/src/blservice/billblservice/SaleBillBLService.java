package blservice.billblservice;

import vo.billvo.SaleBillVO;

public interface SaleBillBLService extends BillBLService {

	/**
	 * �����ݱ��浽���ݿ⣨�ͱ��浥�ݲ�vanȫһ�£�
	 * @return �Ƿ񱣴�ɹ�
	 */
	public boolean saveBill(SaleBillVO bill);
	/**
	 * �������ݿ��е�Bill����
	 * @return
	 */
	public boolean updateBill(SaleBillVO bill);
	/**
	 * ����ὫPOת����VO
	 * @param id ������id���
	 * @return ���۵��ݵ�VO��
	 */
	public SaleBillVO getBill(String id);
}
