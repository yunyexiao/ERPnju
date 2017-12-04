package dataservice;

import java.rmi.RemoteException;

import po.billpo.ReceiptBillPO;

public interface ReceiptBillDataService {
	
	boolean saveBill(ReceiptBillPO receiptBill) throws RemoteException;

	boolean deleteBill(String id) throws RemoteException;
	
	String getNewId() throws RemoteException;
	
	ReceiptBillPO getBillById(String id) throws RemoteException;
	
}
