package blservice.billblservice;

import vo.billvo.ChangeBillVO;

public interface ChangeBillBLService extends BillBLService {

	/**
	 * �����ݱ��浽���ݿ⣨�ͱ��浥�ݲ�vanȫһ�£�
	 * @return �Ƿ񱣴�ɹ�
	 */
	public boolean saveBill(ChangeBillVO bill);
	/**
	 * �������ݿ��е�Bill����
	 * @return
	public boolean updateBill(ChangeBillVO bill);
	 */
	/**
	 * ����ὫPOת����VO
	 * @param id ������id���
	 * @return ���۵��ݵ�VO��
	 */
	public ChangeBillVO getBill(String id);
}
