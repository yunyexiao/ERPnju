package dataservice;

import java.rmi.RemoteException;

import po.billpo.PaymentBillPO;

public interface PaymentBillDataService {
	
	boolean saveBill(PaymentBillPO paymentBill) throws RemoteException;

	boolean deleteBill(String id) throws RemoteException;
	
	String getNewId() throws RemoteException;
	
	PaymentBillPO getBillById(String id) throws RemoteException;
	
}
