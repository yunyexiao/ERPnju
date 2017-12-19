package dataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;

import po.billpo.ChangeBillPO;

public interface ChangeBillDataService extends Remote{

	public ChangeBillPO getBillById(String id) throws RemoteException;
	
	public boolean saveBill(ChangeBillPO bill) throws RemoteException;
	
	public boolean deleteBill(String id) throws RemoteException;
	/**
	 * ���ص��쵥�ݵ����±�ţ�������ID�����һ���֣�����ȫ����
	 * @param isOver �ж��Ƿ�Ϊ���絥-true
	 * @return
	 */
	public String getNewId(boolean isOver) throws RemoteException;
	
}
