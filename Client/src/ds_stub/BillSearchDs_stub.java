package ds_stub;

import java.rmi.RemoteException;
import java.util.ArrayList;

import dataservice.BillSearchDataService;
import po.billpo.*;

public class BillSearchDs_stub implements BillSearchDataService {

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
                , state, "000001", "fa♂q", 15000, items1
            ));
        
        ArrayList<SalesItemsPO> items2 = new ArrayList<>();
        items2.add(new SalesItemsPO("000003", "", 50, 200, 10000));
        
        result.add(new PurchaseBillPO(
                "2017-12-05", "21:30:02", "12000", "0002"
                , state, "000002", "hello susie", 10000, items2
            ));
        System.out.println("Purchase bills of state " + state + " returned from database.");
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
            , state, "000003", "", 4600.0, items2
        ));

        ArrayList<SalesItemsPO> items3 = new ArrayList<>();
        items3.add(new SalesItemsPO("000001", "", 50, 100.0, 5000.0));
        items3.add(new SalesItemsPO("000004", "", 70, 300.0, 21000.0));
        result.add(new PurchaseReturnBillPO(
            "2017-12-02", "19:38:47", "21171", "0007"
            , state, "000002", "", 26000.0, items3
        ));
        System.out.println("Purchase Return Bills of state " + state + " returned from database.");
        return result;
	}

	@Override
	public ArrayList<SalesBillPO> searchSalesBills(String fromDate, String toDate, String customerId,
			String operatorId, int state) throws RemoteException {
        ArrayList<SalesBillPO> result = new ArrayList<>();
        ArrayList<SalesItemsPO> items2 = new ArrayList<>();
        items2.add(new SalesItemsPO("000003", "", 50, 200, 10000));
        
        result.add(new SalesBillPO(
            "2017-12-03", "08:30:02", "12000", "0007", state
            , "000002", "sussie", "hello peppa", "10086", 10000, 1000, 1000, 8000, items2
        ));
        
        ArrayList<SalesItemsPO> items3 = new ArrayList<>();
        items3.add(new SalesItemsPO("000004", "", 100, 100, 10000));
        items3.add(new SalesItemsPO("000003", "", 200, 200, 40000));
        
        result.add(new SalesBillPO(
            "2017-12-03", "11:24:51", "32100", "0002", state
            , "000001", "peppa", "hello sussie", "12300", 50000, 1000, 1000, 48000, items3
        ));
        System.out.println("Sales Bills of state " + state + " returned from database.");
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
				"2017-12-04", "21:03:23", "00123", "0002", state,
				"000001", "", "", "00012", 7000.0, 6800.0, items1
				));
    
		ArrayList<SalesItemsPO> items2 = new ArrayList<>();
		items2.add(new SalesItemsPO("000003", "", 30, 80.0, 2400.0));
		items2.add(new SalesItemsPO("000004", "", 40, 60.0, 2400.0));
		result.add(new SalesReturnBillPO(
				"2017-12-04", "12:23:28", "08193", "0007", state,
				"000002", "", "", "00312", 4800.0, 3800.0, items2
				));
		System.out.println("Sales Return Bills of state " + state + " returned from database.");
		return result;
	}

	@Override
	public ArrayList<CashCostBillPO> searchCashCostBills(String fromDate, String toDate, String customerId,
			String operatorId, int state)
			throws RemoteException {
		ArrayList<CashCostBillPO> result = new ArrayList<>();
		ArrayList<CashCostItem> items1 = new ArrayList<>();
		items1.add(new CashCostItem("香蕉君", 10000, "买香蕉"));
		items1.add(new CashCostItem("魔男", 12345, "去幻想♂乡"));
		result.add(new CashCostBillPO(
				"2017-12-07", "11:03:23", "00123", "0002", state,
				"6209111100001111",  items1, 22345
				));
    
		ArrayList<CashCostItem> items2 = new ArrayList<>();
		items2.add(new CashCostItem("诸葛琴魔", 1000, "买琴"));
		items2.add(new CashCostItem("亚非拉", 1234567, "有了金坷垃，才能种庄稼！"));
		result.add(new CashCostBillPO(
				"2017-12-07", "20:06:37", "23333", "0002", state,
				"6209111100002222",  items2, 1235567
				));
		System.out.println("Cash Cost Bills of state " + state + " returned from database.");
		return result;
	}

	@Override
	public ArrayList<PaymentBillPO> searchPaymentBills(String fromDate, String toDate, String customerId,
			String operatorId, int state)
			throws RemoteException {
		ArrayList<PaymentBillPO> result = new ArrayList<>();
		ArrayList<TransferItem> items1 = new ArrayList<>();
		items1.add(new TransferItem("110", 10000, "赎金"));
		items1.add(new TransferItem("119", 12345, "大保健费用"));
		result.add(new PaymentBillPO(
				"2017-12-06", "07:10:11", "00123", "0002", state,
				"000001",  items1, 22345
				));
    
		ArrayList<TransferItem> items2 = new ArrayList<>();
		items2.add(new TransferItem("114", 100, "无"));
		items2.add(new TransferItem("120", 127, "该内容涉嫌违规，已被删除"));
		result.add(new PaymentBillPO(
				"2017-12-06", "23:19:16", "23333", "0003", state,
				"000001",  items2, 227
				));
		System.out.println("Payment Bills of state " + state + " returned from database.");
		return result;
	}

	@Override
	public ArrayList<ReceiptBillPO> searchReceiptBills(String fromDate, String toDate, String customerId,
			String operatorId, int state)
			throws RemoteException {
		ArrayList<ReceiptBillPO> result = new ArrayList<>();
		ArrayList<TransferItem> items1 = new ArrayList<>();
		items1.add(new TransferItem("110", 10000, "赎金"));
		items1.add(new TransferItem("119", 12345, "大保健费用"));
		result.add(new ReceiptBillPO(
				"2017-12-05", "07:10:11", "00123", "0002", state,
				"000001",  items1, 22345
				));
    
		ArrayList<TransferItem> items2 = new ArrayList<>();
		items2.add(new TransferItem("114", 100, "无"));
		items2.add(new TransferItem("120", 127, "该内容涉嫌违规，已被删除"));
		result.add(new ReceiptBillPO(
				"2017-12-05", "23:19:16", "23333", "0003", state,
				"000001",  items2, 227
				));
		System.out.println("Receipt Bills of state " + state + " returned from database.");
		return result;
	}
	
	@Override
	public ArrayList<ChangeBillPO> searchChangeBills(String fromDate, String toDate, String store,
			String operatorId, boolean isOver, int state) throws RemoteException {
		ArrayList<ChangeBillPO> result = new ArrayList<>();
		ArrayList<ChangeItem> items1 = new ArrayList<>();
		items1.add(new ChangeItem("000001", 20, isOver ? 25 : 15));
		result.add(new ChangeBillPO(
				"2017-12-08", "11:03:23", "00123", "0004", state,
				isOver,  items1
				));
    
		ArrayList<ChangeItem> items2 = new ArrayList<>();
		items2.add(new ChangeItem("000002", 100, isOver ? 150 : 90));
		result.add(new ChangeBillPO(
				"2017-12-08", "20:06:37", "23333", "0006", state,
				isOver,  items2
				));
		System.out.println("Change Bills of state " + state + " returned from database, isOver = " + isOver);
		return result;
	}
}
