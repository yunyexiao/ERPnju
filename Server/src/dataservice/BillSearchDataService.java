package dataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import po.UserPO;
import po.billpo.*;

public interface BillSearchDataService extends Remote{
	 ArrayList<BillPO> getBillList(UserPO user) throws RemoteException;
	
    ArrayList<PurchaseBillPO> searchPurchaseBills(String fromDate, String toDate
        , String customerId, String operatorId, int state) throws RemoteException;

    ArrayList<PurchaseReturnBillPO> searchPurchaseReturnBills(String fromDate, String toDate
        , String customerId, String operatorId, int state) throws RemoteException;

    ArrayList<SalesBillPO> searchSalesBills(String fromDate, String toDate
        , String customerId, String operatorId, int state) throws RemoteException;

    ArrayList<SalesReturnBillPO> searchSalesReturnBills(String fromDate, String toDate
        , String customerId, String operatorId, int state) throws RemoteException;

    ArrayList<CashCostBillPO> searchCashCostBills(String fromDate, String toDate
        , String customerId, String operatorId, int state) throws RemoteException;

    ArrayList<ChangeBillPO> searchChangeBills(String fromDate, String toDate
        , String store, String operatorId, boolean isOver,int state) throws RemoteException;

    ArrayList<PaymentBillPO> searchPaymentBills(String fromDate, String toDate
        , String customerId, String operatorId, int state) throws RemoteException;

    ArrayList<ReceiptBillPO> searchReceiptBills(String fromDate, String toDate
        , String customerId, String operatorId,int state) throws RemoteException;

}
