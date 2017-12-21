package businesslogic;

import java.util.ArrayList;

import bl_stub.CustomerBL_stub;
//import blservice.infoservice.GetCategoryInterface;
import blservice.infoservice.GetCommodityInterface;
import blservice.infoservice.GetCustomerInterface;
import blservice.infoservice.GetUserInterface;
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
import presentation.component.MyTableModel;
import vo.CommodityVO;
import vo.CustomerVO;
import vo.billvo.CashCostBillVO;
import vo.billvo.ChangeBillVO;
import vo.billvo.PaymentBillVO;
import vo.billvo.PurchaseBillVO;
import vo.billvo.PurchaseReturnBillVO;
import vo.billvo.ReceiptBillVO;
import vo.billvo.SalesBillVO;
import vo.billvo.SalesReturnBillVO;

public class BillTools {
	
	private static GetCommodityInterface commodityInfo = new CommodityBL();
	//private static GetCategoryInterface categoryInfo = new CategoryBL();
	private static GetCustomerInterface customerInfo = new CustomerBL();
	private static GetUserInterface userInfo = new UserBL();

	/**
	 * 报溢/报损单据PO向VO的转换，其中库存数量为实时数据
	 * @param bill 
	 * @return
	 */
	public static ChangeBillVO toChangeBillVO(ChangeBillPO bill) {
		String[] headers = {"商品id", "商品名称", "库存数量", "实际数量"};
		int size = bill.getCommodityList().size();
		String[][] data = new String[size][4];
		for (int i = 0; i < size; i++) {
			ChangeItem item = bill.getCommodityList().get(i);
			data[i] = new String[]{item.getCommodityId(), "", ""+item.getOriginalValue(), ""+item.getChangedValue()};
			data[i][1] = commodityInfo.getCommodity(item.getCommodityId()).getName();
			if (bill.getState() != ChangeBillPO.PASS) data[i][2]= ""+commodityInfo.getCommodity(item.getCommodityId()).getAmount();
		}
		return new ChangeBillVO(bill.getDate(), bill.getTime(), bill.getId(), userInfo.getUser(bill.getOperator()).getName(), bill.getState(), bill.getFlag(), new MyTableModel(data, headers));
	}
	
	public static PurchaseBillVO toPurchaseBillVO(PurchaseBillPO bill){
        String[] columnNames = {"商品编号", "名称", "型号", "库存", "单价", "数量", "总价", "备注"};
        ArrayList<SalesItemsPO> items = bill.getPurchaseBillItems();
        String[][] data = new String[items.size()][];
        for(int i = 0; i < data.length; i++){
        	SalesItemsPO item = items.get(i);
        	CommodityVO commodity = new CommodityBL().getCommodity(item.getComId());
            data[i] = new String[]{item.getComId(), commodity.getName()
                    , commodity.getType(), commodity.getStore()
                    , item.getComPrice() + "", item.getComQuantity() + ""
                    , item.getComSum() + "", item.getComRemark()};
        }
        MyTableModel model = new MyTableModel(data, columnNames);
        CustomerVO supplier = customerInfo.getCustomer(bill.getSupplierId());
        return new PurchaseBillVO(bill.getDate(), bill.getTime(), bill.getId(), bill.getOperator()
            , bill.getState(), bill.getSupplierId(), supplier.getName()
            , model, bill.getRemark(), bill.getSum());
    }
	
	public static PurchaseReturnBillVO toPurchaseReturnBillVO(PurchaseReturnBillPO bill){
        String[] columnNames = {"商品编号", "名称", "型号", "库存", "单价", "数量", "总价", "备注"};
        ArrayList<SalesItemsPO> items = bill.getPurchaseReturnBillItems();
        String[][] data = new String[items.size()][columnNames.length];
        for(int i = 0; i < data.length; i++){
        	SalesItemsPO item = items.get(i);
        	CommodityVO c = commodityInfo.getCommodity(item.getComId());
            double price = item.getComPrice();
            int num = item.getComQuantity();
            double sum = price * num;
            data[i] = new String[]{c.getId(), c.getName(), c.getType(), c.getStore(), price + "", num + "", sum + "", item.getComRemark()};
        }
        MyTableModel model = new MyTableModel(data, columnNames);
        CustomerVO customer = customerInfo.getCustomer(bill.getSupplierId());
        return new PurchaseReturnBillVO(bill.getDate(), bill.getTime(), bill.getId()
            , bill.getOperator(), bill.getState(), bill.getSupplierId()
            , customer.getName(), model, bill.getRemark(), bill.getSum());
    }
	
	public static SalesBillVO toSalesBillVO(SalesBillPO bill){
        String[] columnNames = {"编号", "名称", "型号", "库存", "单价", "数量", "总价", "备注"};
        ArrayList<SalesItemsPO> items = bill.getSalesBillItems();
        String[][] data;
        if(items == null) data = null;
        else{
            data = new String[items.size()][];
            for(int i = 0; i < data.length; i++){
            	SalesItemsPO item = items.get(i);
                CommodityVO c = commodityInfo.getCommodity(item.getComId());
                String name = c.getName(),
                        type = c.getType(),
                        store = c.getStore(),
                        price = item.getComPrice() + "",
                        num = item.getComQuantity() + "",
                        sum = item.getComSum() + "",
                        remark = item.getComRemark();
                data[i] = new String[]{item.getComId(), name, type, store, price, num, sum, remark};
            }
        }
        MyTableModel model = new MyTableModel(data, columnNames);
        String date = bill.getDate(),
               time = bill.getTime(),
               id = bill.getId(),
               operatorId = bill.getOperator(),
               cusId = bill.getCustomerId(),
               cusName = new CustomerBL_stub().getCustomer(cusId)
                           .getName(),
               remark = bill.getRemark();
        int state = bill.getState();
        double sum = bill.getAfterDiscount(),
               beforeDiscount = bill.getBeforeDiscount(),
               discount = bill.getDiscount(),
               coupon = bill.getCoupon();
        return new SalesBillVO(date, time, id, operatorId, state
            , cusId, cusName, model, remark, beforeDiscount
            , discount, coupon, sum);
    }
    
	public static SalesReturnBillVO toSalesReturnBillVO(SalesReturnBillPO bill, BillBL billBL){
        String date = bill.getDate(),
               time = bill.getTime(),
               id = bill.getId(),
               operator = bill.getOperator();
        int state = bill.getState();
        String customerId = bill.getCustomerId(),
               customerName = new CustomerBL_stub()
                   .getCustomer(customerId).getName(),
               remark = bill.getRemark(),
               originalSBId = bill.getOriginalSBId();
        SalesBillVO salesBill = billBL.getSalesBill(originalSBId);
        double discountRate = salesBill.getSum() / salesBill.getBeforeDiscount();
        double originalSum = bill.getOriginalSum(),
               sum = bill.getReturnSum();
        String[] columnName = {"商品编号", "名称", "型号", "库存", "单价", "数量", "总价", "备注"};
        ArrayList<SalesItemsPO> items = bill.getSalesReturnBillItems();
        String[][] data = new String[items.size()][];
        for(int i = 0; i < items.size(); i++){
        	SalesItemsPO item = items.get(i);
            CommodityVO commodity = new CommodityBL().getCommodity(item.getComId());
            String name = commodity.getName(),
                   type = commodity.getType(),
                   store = commodity.getStore(),
                   itemremark = item.getComRemark();
            double price = item.getComPrice(), 
                   itemsum = item.getComSum();
            int num = item.getComQuantity();
            data[i] = new String[]{item.getComId(), name, type, store, price + "", num + "", itemsum + "", itemremark};
        }
        MyTableModel model = new MyTableModel(data, columnName);
        return new SalesReturnBillVO(date, time, id, operator
            , state, customerId, customerName, model, remark
            , originalSBId, discountRate, originalSum, sum);
    }
	
	/**
	 * 现金费用单据PO向VO的转换
	 * @param bill 
	 * @return
	 */
	public static CashCostBillVO toCashCostBillVO(CashCostBillPO bill) {
		String[] columnNames = {"条目名", "金额", "备注"};
        ArrayList<CashCostItem> items = bill.getCashcostList();
        String[][] data = new String[items.size()][columnNames.length];
        for(int i = 0; i < data.length; i++){
        	CashCostItem item = items.get(i);
            data[i] =  new String[]{item.getName(), Double.toString(item.getMoney()), item.getRemark()};
        }
        MyTableModel model = new MyTableModel(data, columnNames);
        CashCostBillVO cashCostBillVO = new CashCostBillVO(bill.getDate(), bill.getTime(), bill.getId(), bill.getOperator()
                , bill.getState(), bill.getAccountId(), model);
        return cashCostBillVO;
	}
	
	public static PaymentBillVO toPaymentBillVO(PaymentBillPO bill) {
        String[] columnNames = {"银行账户", "转账金额", "备注"};
        ArrayList<TransferItem> items = bill.getTransferList();
        String[][] data = new String[items.size()][columnNames.length];
        for(int i = 0; i < data.length; i++){
        	TransferItem item = items.get(i);
            data[i] = new String[]{item.getAccountId(), Double.toString(item.getMoney()), item.getRemark()};
        }
        MyTableModel model = new MyTableModel(data, columnNames);
        PaymentBillVO paymentBillVO = new PaymentBillVO(bill.getDate(), bill.getTime(), bill.getId(), bill.getOperator()
                , bill.getState(), bill.getCustomerId());
        paymentBillVO.setTableModel(model);
        return paymentBillVO;
    }
	
	public static ReceiptBillVO toReceiptBillVO(ReceiptBillPO bill){
        String[] columnNames = {"银行账户", "转账金额", "备注"};
        ArrayList<TransferItem> items = bill.getTransferList();
        String[][] data = new String[items.size()][columnNames.length];
        for(int i = 0; i < data.length; i++){
        	TransferItem item = items.get(i);
        	data[i] = new String[]{item.getAccountId(), Double.toString(item.getMoney()), item.getRemark()};
        }
        MyTableModel model = new MyTableModel(data, columnNames);
        ReceiptBillVO receiptBillVO = new ReceiptBillVO(bill.getDate(), bill.getTime(), bill.getId(), bill.getOperator()
                , bill.getState(), bill.getCustomerId(), model);
        return receiptBillVO;
    }
	/**
	 * 根据billPO的子类类型获得单据的中文名称
	 * @param bill billPO对象
	 * @return
	 */
	public static String getBillName(BillPO bill) {
		if (bill instanceof ChangeBillPO) return ((ChangeBillPO)bill).getFlag() ? "报溢单":"报损单";
		else if (bill instanceof SalesBillPO) return "销售单";
		else if (bill instanceof SalesReturnBillPO) return "销售退货单";
		else if (bill instanceof PurchaseBillPO) return "进货单";
		else if (bill instanceof PurchaseReturnBillPO) return "进货退货单";
		else if (bill instanceof PaymentBillPO) return "付款单";
		else if (bill instanceof ReceiptBillPO) return "收款单";
		else if (bill instanceof CashCostBillPO) return "现金费用单";
		return "未知类型";
	}
}
