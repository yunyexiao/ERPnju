package ds_stub;

import java.util.ArrayList;

import dataservice.BillDataService;
import po.UserPO;
import po.billpo.BillPO;
import po.billpo.ChangeBillPO;
import po.billpo.ChangeItem;
import po.billpo.PurchaseBillPO;
import po.billpo.PurchaseReturnBillPO;
import po.billpo.SalesBillPO;
import po.billpo.SalesItemsPO;
import po.billpo.SalesReturnBillPO;

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
			list.add(getPurchaseBill(""));
			list.add(getPurchaseReturnBill(""));
			list.add(getSalesBill(""));
			list.add(getSalesReturnBill(""));
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
}
