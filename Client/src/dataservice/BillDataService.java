package dataservice;

import java.util.ArrayList;

import po.UserPO;
import po.billpo.BillPO;
import po.billpo.ChangeBillPO;
import po.billpo.PurchaseBillPO;
import po.billpo.PurchaseReturnBillPO;
import po.billpo.SalesBillPO;
import po.billpo.SalesReturnBillPO;

public interface BillDataService {
	
	public ArrayList<BillPO> getBillList(UserPO user);
	
	public ChangeBillPO getChangeBill(String id);
	
	public PurchaseBillPO getPurchaseBill(String id);
	
	public PurchaseReturnBillPO getPurchaseReturnBill(String id);
	
	public SalesBillPO getSalesBill(String id);
	
	public SalesReturnBillPO getSalesReturnBill(String id);
}
