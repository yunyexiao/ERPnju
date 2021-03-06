package dataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import po.billpo.ReceiptBillPO;

public interface ReceiptBillDataService extends Remote{

	public boolean saveBill(ReceiptBillPO bill) throws RemoteException;

	public boolean deleteBill(String billid) throws RemoteException;

	public String getNewId() throws RemoteException;
	
	public ReceiptBillPO getBillById(String id) throws RemoteException;
	
    ArrayList<ReceiptBillPO> getBillsByDate(String from, String to) throws RemoteException;

}
