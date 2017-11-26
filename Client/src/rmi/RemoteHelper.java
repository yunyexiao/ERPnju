package rmi;

import java.rmi.Remote;

import dataservice.AccountDataService;
import dataservice.CategoryDataService;
import dataservice.CommodityDataService;
import dataservice.CustomerDataService;
import dataservice.LogDataService;
import dataservice.UserDataService;

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
	
	public UserDataService getUserDataService() {
		return (UserDataService) remote;
	}
	
	public AccountDataService getAccountDataService() {
		return (AccountDataService) remote;
	}
	
	public CategoryDataService getCategoryDataService() {
		return (CategoryDataService) remote;
	}
	
	public CommodityDataService getCommodityDataService() {
		return (CommodityDataService) remote;
	}

	public CustomerDataService getCustomerDataService() {
		return (CustomerDataService) remote;
	}
	
	public LogDataService getLogDataService() {
		return (LogDataService) remote;
	}
}
