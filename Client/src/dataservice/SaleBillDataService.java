package dataservice;

import po.billpo.SaleBillPO;

public interface SaleBillDataService {

	/**
	 * ÿһ�ֵ��ݶ���Ӧһ��BillData
	 * ����û�����������͸��£���������������һ�£�
	 * @param bill
	 * @return
	 */
	public boolean saveBill(SaleBillPO bill);
	/**
	 * ���ݱ��ɾ��һ�ŵ���
	 * @param billid
	 * @return
	 */
	public boolean deleteBill(String billid);
	/**
	 * ������۵��ݵ��±��
	 * @return
	 */
	public String getNewId();
	
	public SaleBillPO getBillById(String id);
}
