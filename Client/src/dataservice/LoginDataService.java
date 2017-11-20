package dataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;

import po.UserPO;

public interface LoginDataService extends Remote {

	/**
	 * 根据用户的ID返回一个UserPO对象
	 * @param id 用户的ID
	 * @return 用户存在则返回userPO对象，否则返回null
	 * @throws RemoteException
	 */
	public UserPO findById(String id) throws RemoteException;
	
}
