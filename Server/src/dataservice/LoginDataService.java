package dataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;

import po.UserPO;

public interface LoginDataService extends Remote {

	/**
	 * �����û���ID����һ��UserPO����
	 * @param id �û���ID
	 * @return �û������򷵻�userPO���󣬷��򷵻�null
	 * @throws RemoteException
	 */
	public UserPO findById(String id) throws RemoteException;
	
}
