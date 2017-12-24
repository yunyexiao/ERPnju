package data;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import dataservice.BillDataService;
import po.UserPO;
import po.billpo.*;

public class BillData extends UnicastRemoteObject implements BillDataService {

	public BillData() throws RemoteException {
		super();
	}

	@Override
	public ArrayList<BillPO> getBillList(UserPO user) throws RemoteException {

		ArrayList<BillPO> bills=new ArrayList<BillPO>();
		BillSearchData billSearch=new BillSearchData();
		Calendar now = Calendar.getInstance(); 
		String today=now.get(Calendar.YEAR)+"-"+now.get(Calendar.MONTH)+"-"+now.get(Calendar.DAY_OF_MONTH);
		String start="2000-01-01";
		String userId=user.getUserId();
	
		if(user.getUsertype()==0){
			try{
				ArrayList<ChangeBillPO> lostBills=billSearch.searchChangeBills(start, today, null, userId,false, -1);
				ArrayList<ChangeBillPO> overflowBills=billSearch.searchChangeBills(start, today, null, userId, true, -1);
				bills.addAll(lostBills);
				bills.addAll(overflowBills);	
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
		}
		//销售人员
		else if(user.getUsertype()==1){
			ArrayList<SalesBillPO> salesBills=billSearch.searchSalesBills(start, today, null, userId, -1);
			ArrayList<SalesReturnBillPO> salesReturnBills=billSearch.searchSalesReturnBills(start, today, null, userId, -1);
			ArrayList<PurchaseBillPO> purchaseBills=billSearch.searchPurchaseBills(start, today, null, userId, -1);
			ArrayList<PurchaseReturnBillPO> purchaseReturnBills=billSearch.searchPurchaseReturnBills(start, today, null, userId, -1);
			bills.addAll(purchaseReturnBills);
			bills.addAll(purchaseBills);
			bills.addAll(salesReturnBills);
			bills.addAll(salesBills);
		}
		//财务人员
		else if(user.getUsertype()==2){
			ArrayList<CashCostBillPO> cashCostBills=billSearch.searchCashCostBills(start, today, null, userId, -1);
			ArrayList<PaymentBillPO> paymentBills=billSearch.searchPaymentBills(start, today, null, userId, -1);
			ArrayList<ReceiptBillPO> receiptBills=billSearch.searchReceiptBills(start, today, null, userId, -1);
			bills.addAll(receiptBills);
			bills.addAll(cashCostBills);
			bills.addAll(paymentBills);
		}
		return bills;
	}

	@Override
	public ChangeBillPO getChangeBill(String id) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PurchaseBillPO getPurchaseBill(String id) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PurchaseReturnBillPO getPurchaseReturnBill(String id) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SalesBillPO getSalesBill(String id) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SalesReturnBillPO getSalesReturnBill(String id) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CashCostBillPO getCashCostBillPO(String id) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaymentBillPO getPaymentBillPO(String id) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReceiptBillPO getReceiptBillPO(String id) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
