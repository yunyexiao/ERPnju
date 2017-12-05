package dataservice;

import po.billpo.CashCostBillPO;

public interface CashCostBillDataService {

	/**
	 * ÿһ�ֵ��ݶ���Ӧһ��BillData
	 * ����û�����������͸��£���������������һ�£�
	 * @param bill
	 * @return
	 */
	public boolean saveBill(CashCostBillPO bill);
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
	
	public CashCostBillPO getBillById(String id);
}