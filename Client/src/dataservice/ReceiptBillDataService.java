package dataservice;

import java.rmi.RemoteException;

import po.billpo.ReceiptBillPO;

public interface ReceiptBillDataService {

	public boolean saveBill(ReceiptBillPO bill) throws RemoteException;

	public boolean deleteBill(String billid) throws RemoteException;

	public String getNewId() throws RemoteException;
	
	public ReceiptBillPO getBillById(String id) throws RemoteException;
}
