package dataservice;

import po.billpo.ChangeBillPO;

public interface ChangeBillDataService {

	public ChangeBillPO getBillById(String id);
	
	public boolean saveBill(ChangeBillPO bill);
	
	public boolean deleteBill(String id);
	/**
	 * ���ص��쵥�ݵ����±�ţ�������ID�����һ���֣�����ȫ����
	 * @param isOver �ж��Ƿ�Ϊ���絥-true
	 * @return
	 */
	public String getNewId(boolean isOver);
	
}
