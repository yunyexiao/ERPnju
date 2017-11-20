package rmi;

import java.rmi.Remote;

import dataservice.LoginDataService;

public class RemoteHelper {

	private Remote remote;
	private static RemoteHelper remoteHelper = new RemoteHelper();
	public static RemoteHelper getInstance(){
		return remoteHelper;
	}
	
	private RemoteHelper() {
	}
	
	public void setRemote(Remote remote) {
		this.remote = remote;
	}
	
	public LoginDataService getLoginDataService() {
		return (LoginDataService) remote;
	}
}
