package ds_stub;

import java.rmi.RemoteException;
import java.util.ArrayList;

import dataservice.BillSearchDataService;
import po.UserPO;
import po.billpo.*;

public class BillSearchDs_stub implements BillSearchDataService {
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
	
	public ChangeBillPO getChangeBill(String id) {
		ArrayList<ChangeItem> commodityList = new ArrayList<ChangeItem>();
		commodityList.add(new ChangeItem("000002", 100, 80));
		commodityList.add(new ChangeItem("000003", 20, 10));
		return new ChangeBillPO("20171103", "16:19:31","00001","0001", BillPO.PASS, false, commodityList);
	}
	public PurchaseBillPO getPurchaseBill(String id) {
		ArrayList<SalesItemsPO> items1 = new ArrayList<>();
        items1.add(new SalesItemsPO("000001", "", 50, 100, 5000));
        items1.add(new SalesItemsPO("000002", "", 100, 100, 10000));
        return new PurchaseBillPO( "20171205", "19:23:55", "00002", "0002", BillPO.PASS, "000002", "beizhu", 15000, items1);
	}
	public PurchaseReturnBillPO getPurchaseReturnBill(String id) {
		ArrayList<SalesItemsPO> items2 = new ArrayList<>();
        items2.add(new SalesItemsPO("000003", "", 10, 60.0, 600.0));
        items2.add(new SalesItemsPO("000004", "", 50, 80.0, 4000.0));
        return new PurchaseReturnBillPO("20171213", "18:33:47", "01921", "0002", BillPO.PASS, "000001", "", 4600.0, items2);
	}
	public SalesBillPO getSalesBill(String id) {
		ArrayList<SalesItemsPO> items3 = new ArrayList<>();
        items3.add(new SalesItemsPO("000004", "", 100, 100, 10000));
        items3.add(new SalesItemsPO("000003", "", 200, 200, 40000));
        return new SalesBillPO("20171208", "11:24:51", "32100", "0002", BillPO.PASS, "000004", "rarara", null, "12300", 50000, 1000, 1000, 48000, items3);
	}
	public SalesReturnBillPO getSalesReturnBill(String id) {
		ArrayList<SalesItemsPO> items1 = new ArrayList<>();
        items1.add(new SalesItemsPO("000001", "", 100, 50.0, 5000.0));
        items1.add(new SalesItemsPO("000002", "", 20, 100.0, 2000.0));
        return new SalesReturnBillPO("20171209", "21:03:23", "00123", "0002", BillPO.PASS,"000003", "", "", "00012", 7000.0, 6800.0, items1);
	}

	public CashCostBillPO getCashCostBill(String id) {
		ArrayList<CashCostItem> items1 = new ArrayList<>();
		items1.add(new CashCostItem("�㽶��", 10000, "���㽶"));
		items1.add(new CashCostItem("ħ��", 12345, "ȥ�������"));
		items1.add(new CashCostItem("�Ƿ���", 1234567, "���˽������������ׯ�ڣ�"));
		return new CashCostBillPO("20171207", "11:03:23", "00023", "0003", BillPO.COMMITED,"6209111100001111", items1, 22345);
	}

	public PaymentBillPO getPaymentBill(String id) {
		ArrayList<TransferItem> items1 = new ArrayList<>();
        items1.add(new TransferItem("110", 10000, "���"));
		items1.add(new TransferItem("119", 12345, "�󱣽�����"));
		items1.add(new TransferItem("404", 127, "����������Υ�棬�ѱ�ɾ��"));
		return new PaymentBillPO("20171206", "23:19:16", "23333", "0003", BillPO.COMMITED,"000002",  items1, 227);
	}

	public ReceiptBillPO getReceiptBill(String id) {
		ArrayList<TransferItem> items1 = new ArrayList<>();
        items1.add(new TransferItem("110", 10000, "���"));
        items1.add(new TransferItem("·����", 100, "��"));
		return new ReceiptBillPO("20171205", "07:10:11", "00123", "0002", BillPO.COMMITED,"000001",  items1, 22345);
	}
    public ArrayList<PurchaseBillPO> searchPurchaseBills(String fromDate, String toDate, String customerId,
        String operatorId) throws RemoteException {
        ArrayList<PurchaseBillPO> result = new ArrayList<>();
        ArrayList<SalesItemsPO> items1 = new ArrayList<>();
        items1.add(new SalesItemsPO("000001", "", 50, 100, 5000));
        items1.add(new SalesItemsPO("000002", "", 100, 100, 10000));
        
        result.add(new PurchaseBillPO(
            "2017-12-05", "19:23:55", "12345", "0002"
            , BillPO.PASS, "000001", null, 15000, items1
        ));
        
        ArrayList<SalesItemsPO> items2 = new ArrayList<>();
        items2.add(new SalesItemsPO("000003", "", 50, 200, 10000));
        
        result.add(new PurchaseBillPO(
            "2017-12-01", "08:30:02", "12000", "0007"
            , BillPO.PASS, "000002", null, 10000, items2
        ));
        return result;
    }

    @Override
	public ArrayList<PurchaseBillPO> searchPurchaseBills(String fromDate, String toDate, String customerId,
			String operatorId, int state)
			throws RemoteException {
        ArrayList<PurchaseBillPO> result = new ArrayList<>();
        ArrayList<SalesItemsPO> items1 = new ArrayList<>();
        items1.add(new SalesItemsPO("000001", "", 50, 100, 5000));
        items1.add(new SalesItemsPO("000002", "", 100, 100, 10000));
        
        result.add(new PurchaseBillPO(
                "2017-12-05", "19:23:55", "12345", "0007" 
                , BillPO.COMMITED, "000001", "fa��q", 15000, items1
            ));
        
        ArrayList<SalesItemsPO> items2 = new ArrayList<>();
        items2.add(new SalesItemsPO("000003", "", 50, 200, 10000));
        
        result.add(new PurchaseBillPO(
                "2017-12-05", "21:30:02", "12000", "0002"
                , BillPO.COMMITED, "000002", "hello susie", 10000, items2
            ));
		return result;
	}

	@Override
	public ArrayList<PurchaseReturnBillPO> searchPurchaseReturnBills(String fromDate, String toDate, String customerId,
			String operatorId, int state)
			throws RemoteException {
        ArrayList<PurchaseReturnBillPO> result = new ArrayList<>();
        ArrayList<SalesItemsPO> items2 = new ArrayList<>();
        items2.add(new SalesItemsPO("000003", "", 10, 60.0, 600.0));
        items2.add(new SalesItemsPO("000004", "", 50, 80.0, 4000.0));
        result.add(new PurchaseReturnBillPO(
            "2017-12-02", "18:33:47", "01921", "0007"
            , BillPO.COMMITED, "000003", "", 4600.0, items2
        ));

        ArrayList<SalesItemsPO> items3 = new ArrayList<>();
        items3.add(new SalesItemsPO("000001", "", 50, 100.0, 5000.0));
        items3.add(new SalesItemsPO("000004", "", 70, 300.0, 21000.0));
        result.add(new PurchaseReturnBillPO(
            "2017-12-02", "19:38:47", "21171", "0007"
            , BillPO.COMMITED, "000002", "", 26000.0, items3
        ));
        return result;
	}

	@Override
	public ArrayList<SalesBillPO> searchSalesBills(String fromDate, String toDate, String customerId,
			String operatorId, int state) throws RemoteException {
        ArrayList<SalesBillPO> result = new ArrayList<>();
        ArrayList<SalesItemsPO> items2 = new ArrayList<>();
        items2.add(new SalesItemsPO("000003", "", 50, 200, 10000));
        
        result.add(new SalesBillPO(
            "2017-12-03", "08:30:02", "12000", "0007", BillPO.PASS
            , "000002", "sussie", "hello peppa", "10086", 10000, 1000, 1000, 8000, items2
        ));
        
        ArrayList<SalesItemsPO> items3 = new ArrayList<>();
        items3.add(new SalesItemsPO("000004", "", 100, 100, 10000));
        items3.add(new SalesItemsPO("000003", "", 200, 200, 40000));
        
        result.add(new SalesBillPO(
            "2017-12-03", "11:24:51", "32100", "0002", BillPO.COMMITED
            , "000001", "peppa", "hello sussie", "12300", 50000, 1000, 1000, 48000, items3
        ));
        return result;
	}

	@Override
	public ArrayList<SalesReturnBillPO> searchSalesReturnBills(String fromDate, String toDate, String customerId,
			String operatorId, int state){
		ArrayList<SalesReturnBillPO> result = new ArrayList<>();
		ArrayList<SalesItemsPO> items1 = new ArrayList<>();
		items1.add(new SalesItemsPO("000001", "", 100, 50.0, 5000.0));
		items1.add(new SalesItemsPO("000002", "", 20, 100.0, 2000.0));
		result.add(new SalesReturnBillPO(
				"2017-12-04", "21:03:23", "00123", "0002", BillPO.COMMITED,
				"000001", "", "", "00012", 7000.0, 6800.0, items1
				));
    
		ArrayList<SalesItemsPO> items2 = new ArrayList<>();
		items2.add(new SalesItemsPO("000003", "", 30, 80.0, 2400.0));
		items2.add(new SalesItemsPO("000004", "", 40, 60.0, 2400.0));
		result.add(new SalesReturnBillPO(
				"2017-12-04", "12:23:28", "08193", "0007", BillPO.COMMITED,
				"000002", "", "", "00312", 4800.0, 3800.0, items2
				));
		return result;
	}

	@Override
	public ArrayList<CashCostBillPO> searchCashCostBills(String fromDate, String toDate, String customerId,
			String operatorId, int state)
			throws RemoteException {
		ArrayList<CashCostBillPO> result = new ArrayList<>();
		ArrayList<CashCostItem> items1 = new ArrayList<>();
		items1.add(new CashCostItem("�㽶��", 10000, "���㽶"));
		items1.add(new CashCostItem("ħ��", 12345, "ȥ�������"));
		result.add(new CashCostBillPO(
				"2017-12-07", "11:03:23", "00123", "0002", BillPO.COMMITED,
				"6209111100001111",  items1, 22345
				));
    
		ArrayList<CashCostItem> items2 = new ArrayList<>();
		items2.add(new CashCostItem("�����ħ", 1000, "����"));
		items2.add(new CashCostItem("�Ƿ���", 1234567, "���˽������������ׯ�ڣ�"));
		result.add(new CashCostBillPO(
				"2017-12-07", "20:06:37", "23333", "0002", BillPO.COMMITED,
				"6209111100002222",  items2, 1235567
				));
		return result;
	}

	@Override
	public ArrayList<PaymentBillPO> searchPaymentBills(String fromDate, String toDate, String customerId,
			String operatorId, int state)
			throws RemoteException {
		ArrayList<PaymentBillPO> result = new ArrayList<>();
		ArrayList<TransferItem> items1 = new ArrayList<>();
		items1.add(new TransferItem("110", 10000, "���"));
		items1.add(new TransferItem("119", 12345, "�󱣽�����"));
		result.add(new PaymentBillPO(
				"2017-12-06", "07:10:11", "00123", "0002", BillPO.COMMITED,
				"000001",  items1, 22345
				));
    
		ArrayList<TransferItem> items2 = new ArrayList<>();
		items2.add(new TransferItem("114", 100, "��"));
		items2.add(new TransferItem("120", 127, "����������Υ�棬�ѱ�ɾ��"));
		result.add(new PaymentBillPO(
				"2017-12-06", "23:19:16", "23333", "0003", BillPO.COMMITED,
				"000001",  items2, 227
				));
		return result;
	}

	@Override
	public ArrayList<ReceiptBillPO> searchReceiptBills(String fromDate, String toDate, String customerId,
			String operatorId, int state)
			throws RemoteException {
		ArrayList<ReceiptBillPO> result = new ArrayList<>();
		ArrayList<TransferItem> items1 = new ArrayList<>();
		items1.add(new TransferItem("110", 10000, "���"));
		items1.add(new TransferItem("119", 12345, "�󱣽�����"));
		result.add(new ReceiptBillPO(
				"2017-12-05", "07:10:11", "00123", "0002", BillPO.COMMITED,
				"000001",  items1, 22345
				));
    
		ArrayList<TransferItem> items2 = new ArrayList<>();
		items2.add(new TransferItem("114", 100, "��"));
		items2.add(new TransferItem("120", 127, "����������Υ�棬�ѱ�ɾ��"));
		result.add(new ReceiptBillPO(
				"2017-12-05", "23:19:16", "23333", "0003", BillPO.COMMITED,
				"000001",  items2, 227
				));
		return result;
	}
	
	@Override
	public ArrayList<ChangeBillPO> searchChangeBills(String fromDate, String toDate, String store, String operatorId,
			boolean isOver, int state) throws RemoteException {
		ArrayList<ChangeBillPO> result = new ArrayList<>();
		ArrayList<ChangeItem> items1 = new ArrayList<>();
		items1.add(new ChangeItem("000001", 20, 21));
		result.add(new ChangeBillPO(
				"2017-12-08", "11:03:23", "00123", "0004", BillPO.COMMITED,
				true,  items1
				));
    
		ArrayList<ChangeItem> items2 = new ArrayList<>();
		items2.add(new ChangeItem("000002", 100, 90));
		result.add(new ChangeBillPO(
				"2017-12-08", "20:06:37", "23333", "0006", BillPO.COMMITED,
				true,  items2
				));
		return result;
	}
}