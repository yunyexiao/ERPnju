package dataservice;

import java.rmi.RemoteException;
import java.util.ArrayList;

import po.billpo.*;

public interface BillSearchDataService {
	 ArrayList<PurchaseBillPO> searchPurchaseBills(String fromDate, String toDate, String customerId, String operatorId) throws RemoteException;;
	
	 ArrayList<PurchaseReturnBillPO> searchPurchaseReturnBills(String fromDate, String toDate, String customerId, String operatorId) throws RemoteException;
	
	 ArrayList<SalesBillPO> searchSalesBills(String fromDate, String toDate, String customerId, String operatorId) throws RemoteException;
	 
	 ArrayList<SalesReturnBillPO> searchSalesReturnBills(String fromDate, String toDate, String customerId, String operatorId) throws RemoteException;
	 
	 ArrayList<CashCostBillPO> searchCashCostBills(String fromDate, String toDate, String customerId, String operatorId) throws RemoteException;
	 
	 ArrayList<ChangeBillPO> searchChangeBills(String fromDate, String toDate, String store, String operatorId, boolean isOver) throws RemoteException;
	 
	 ArrayList<PaymentBillPO> searchPaymentBills(String fromDate, String toDate, String customerId, String operatorId) throws RemoteException;
	 
	 ArrayList<ReceiptBillPO> searchReceiptBills(String fromDate, String toDate, String customerId, String operatorId) throws RemoteException;

}
