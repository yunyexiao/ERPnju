package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import data.LoginData;
import dataservice.LoginDataService;
import po.UserPO;

public class DataRemoteObject implements Remote, LoginDataService {

	private LoginDataService loginDataService;
	
	protected DataRemoteObject() {
		loginDataService = new LoginData();
	}
	
	@Override
	public UserPO findById(String id) throws RemoteException {
		return loginDataService.findById(id);
	}

}
