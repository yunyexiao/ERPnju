package dataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import po.UserPO;
import po.billpo.BillPO;
import po.billpo.CashCostBillPO;
import po.billpo.ChangeBillPO;
import po.billpo.PaymentBillPO;
import po.billpo.PurchaseBillPO;
import po.billpo.PurchaseReturnBillPO;
import po.billpo.ReceiptBillPO;
import po.billpo.SalesBillPO;
import po.billpo.SalesReturnBillPO;

public interface BillDataService extends Remote{
	
	public ArrayList<BillPO> getBillList(UserPO user) throws RemoteException;
	
	public ChangeBillPO getChangeBill(String id) throws RemoteException;
	
	public PurchaseBillPO getPurchaseBill(String id) throws RemoteException;
	
	public PurchaseReturnBillPO getPurchaseReturnBill(String id) throws RemoteException;
	
	public SalesBillPO getSalesBill(String id) throws RemoteException;
	
	public SalesReturnBillPO getSalesReturnBill(String id) throws RemoteException;
	
	public CashCostBillPO getCashCostBillPO(String id) throws RemoteException;
	
	public PaymentBillPO getPaymentBillPO(String id) throws RemoteException;
	
	public ReceiptBillPO getReceiptBillPO(String id) throws RemoteException;
}
