package blservice.billblservice;

import vo.billvo.ChangeBillVO;

public interface ChangeBillBLService extends BillCreateBLService {

	/**
	 * �����ݱ��浽���ݿ⣨�ͱ��浥�ݲ�vanȫһ�£�
	 * @return �Ƿ񱣴�ɹ�
	 */
	public boolean saveBill(ChangeBillVO bill);
}
