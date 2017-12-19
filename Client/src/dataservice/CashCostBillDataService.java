package dataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import po.billpo.CashCostBillPO;

public interface CashCostBillDataService extends Remote{

	public boolean saveBill(CashCostBillPO bill) throws RemoteException;

	public boolean deleteBill(String billid) throws RemoteException;

	public String getNewId() throws RemoteException;
	
	public CashCostBillPO getBillById(String id) throws RemoteException;
}