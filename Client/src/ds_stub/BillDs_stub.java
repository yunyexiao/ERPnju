package ds_stub;

import java.util.ArrayList;

import dataservice.BillDataService;
import po.UserPO;
import po.billpo.BillPO;
import po.billpo.CashCostBillPO;
import po.billpo.CashCostItem;
import po.billpo.ChangeBillPO;
import po.billpo.ChangeItem;
import po.billpo.PaymentBillPO;
import po.billpo.PurchaseBillPO;
import po.billpo.PurchaseReturnBillPO;
import po.billpo.ReceiptBillPO;
import po.billpo.SalesBillPO;
import po.billpo.SalesItemsPO;
import po.billpo.SalesReturnBillPO;
import po.billpo.TransferItem;

public class BillDs_stub implements BillDataService {

	ArrayList<BillPO> list = new ArrayList<BillPO>();
	
	@Override
	public ArrayList<BillPO> getBillList(UserPO user) {
		ArrayList<ChangeItem> commodityList = new ArrayList<ChangeItem>();
		if (user.getUsertype() == UserPO.UserType.STORE_KEEPER) {
			commodityList.add(new ChangeItem("000002", 100, 80));
			commodityList.add(new ChangeItem("000003", 20, 10));
			list.add(new ChangeBillPO("20171103", "16:19:31","00001","0001", BillPO.SAVED, false, commodityList));
			list.add(new ChangeBillPO("20171103", "12:19:31","00002","0001", BillPO.NOTPASS, false, commodityList));
			commodityList.clear();
			commodityList.add(new ChangeItem("000002", 60, 80));
			list.add(new ChangeBillPO("20171105", "11:19:31","00001","0001", BillPO.SAVED, true, commodityList));
		} else if (user.getUsertype() == UserPO.UserType.SALESMAN) {
			list.add(getPurchaseBill(null));
			list.add(getPurchaseReturnBill(null));
			list.add(getSalesBill(null));
			list.add(getSalesReturnBill(null));
		} else if (user.getUsertype() == UserPO.UserType.ACCOUNTANT) {
			list.add(getCashCostBill(null));
			list.add(getPaymentBill(null));
			list.add(getReceiptBill(null));
		}
		return list;
	}
	
	@Override
	public ChangeBillPO getChangeBill(String id) {
		ArrayList<ChangeItem> commodityList = new ArrayList<ChangeItem>();
		commodityList.add(new ChangeItem("000002", 100, 80));
		commodityList.add(new ChangeItem("000003", 20, 10));
		return new ChangeBillPO("20171103", "16:19:31","00001","0001", BillPO.PASS, false, commodityList);
	}
	@Override
	public PurchaseBillPO getPurchaseBill(String id) {
		ArrayList<SalesItemsPO> items1 = new ArrayList<>();
        items1.add(new SalesItemsPO("000001", "", 50, 100, 5000));
        items1.add(new SalesItemsPO("000002", "", 100, 100, 10000));
        return new PurchaseBillPO( "20171205", "19:23:55", "00002", "0002", BillPO.PASS, "000002", "beizhu", 15000, items1);
	}
	@Override
	public PurchaseReturnBillPO getPurchaseReturnBill(String id) {
		ArrayList<SalesItemsPO> items2 = new ArrayList<>();
        items2.add(new SalesItemsPO("000003", "", 10, 60.0, 600.0));
        items2.add(new SalesItemsPO("000004", "", 50, 80.0, 4000.0));
        return new PurchaseReturnBillPO("20171213", "18:33:47", "01921", "0002", BillPO.PASS, "000001", "", 4600.0, items2);
	}
	@Override
	public SalesBillPO getSalesBill(String id) {
		ArrayList<SalesItemsPO> items3 = new ArrayList<>();
        items3.add(new SalesItemsPO("000004", "", 100, 100, 10000));
        items3.add(new SalesItemsPO("000003", "", 200, 200, 40000));
        return new SalesBillPO("20171208", "11:24:51", "32100", "0002", BillPO.PASS, "000004", "rarara", null, "12300", 50000, 1000, 1000, 48000, items3);
	}
	@Override
	public SalesReturnBillPO getSalesReturnBill(String id) {
		ArrayList<SalesItemsPO> items1 = new ArrayList<>();
        items1.add(new SalesItemsPO("000001", "", 100, 50.0, 5000.0));
        items1.add(new SalesItemsPO("000002", "", 20, 100.0, 2000.0));
        return new SalesReturnBillPO("20171209", "21:03:23", "00123", "0002", BillPO.PASS,"000003", "", "", "00012", 7000.0, 6800.0, items1);
	}

	@Override
	public CashCostBillPO getCashCostBill(String id) {
		ArrayList<CashCostItem> items1 = new ArrayList<>();
		items1.add(new CashCostItem("香蕉君", 10000, "买香蕉"));
		items1.add(new CashCostItem("魔男", 12345, "去幻想♂乡"));
		items1.add(new CashCostItem("亚非拉", 1234567, "有了金坷垃，才能种庄稼！"));
		return new CashCostBillPO("20171207", "11:03:23", "00023", "0003", BillPO.COMMITED,"6209111100001111", items1, 22345);
	}

	@Override
	public PaymentBillPO getPaymentBill(String id) {
		ArrayList<TransferItem> items1 = new ArrayList<>();
        items1.add(new TransferItem("110", 10000, "赎金"));
		items1.add(new TransferItem("119", 12345, "大保健费用"));
		items1.add(new TransferItem("404", 127, "该内容涉嫌违规，已被删除"));
		return new PaymentBillPO("20171206", "23:19:16", "23333", "0003", BillPO.COMMITED,"000002",  items1, 227);
	}

	@Override
	public ReceiptBillPO getReceiptBill(String id) {
		ArrayList<TransferItem> items1 = new ArrayList<>();
        items1.add(new TransferItem("110", 10000, "赎金"));
        items1.add(new TransferItem("路边社", 100, "无"));
		return new ReceiptBillPO("20171205", "07:10:11", "00123", "0002", BillPO.COMMITED,"000001",  items1, 22345);
	}
}
