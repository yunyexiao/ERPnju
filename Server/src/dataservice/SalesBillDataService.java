package dataservice;

import java.rmi.RemoteException;
import java.util.ArrayList;

import po.billpo.SalesBillPO;

public interface SalesBillDataService {

	/**
	 * ÿһ�ֵ��ݶ���Ӧһ��BillData
	 * ����û�����������͸��£���������������һ�£�
	 * @param bill
	 * @return
	 */
	public boolean saveBill(SalesBillPO bill) throws RemoteException;
	/**
	 * ���ݱ��ɾ��һ�ŵ���
	 * @param billid
	 * @return
	 */
	public boolean deleteBill(String billid) throws RemoteException;
	/**
	 * ������۵��ݵ��±��
	 * @return
	 */
	public String getNewId() throws RemoteException;
	
	public SalesBillPO getBillById(String id) throws RemoteException;
	
	public ArrayList<SalesBillPO> getBillsBy(String field, String key, boolean isFuzzy) throws RemoteException;
}
