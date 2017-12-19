package dataservice;

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

public interface BillDataService {
	
	public ArrayList<BillPO> getBillList(UserPO user);
	
	public ChangeBillPO getChangeBill(String id);
	
	public PurchaseBillPO getPurchaseBill(String id);
	
	public PurchaseReturnBillPO getPurchaseReturnBill(String id);
	
	public SalesBillPO getSalesBill(String id);
	
	public SalesReturnBillPO getSalesReturnBill(String id);
	
	public CashCostBillPO getCashCostBillPO(String id);
	
	public PaymentBillPO getPaymentBillPO(String id);
	
	public ReceiptBillPO getReceiptBillPO(String id);
}
