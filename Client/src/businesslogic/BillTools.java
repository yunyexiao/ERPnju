package businesslogic;

import blservice.infoservice.GetCategoryInterface;
import blservice.infoservice.GetCommodityInterface;
import blservice.infoservice.GetUserInterface;
import po.billpo.BillPO;
import po.billpo.CashCostBillPO;
import po.billpo.ChangeBillPO;
import po.billpo.ChangeItem;
import po.billpo.PaymentBillPO;
import po.billpo.PurchaseBillPO;
import po.billpo.PurchaseReturnBillPO;
import po.billpo.ReceiptBillPO;
import po.billpo.SalesBillPO;
import po.billpo.SalesReturnBillPO;
import presentation.component.MyTableModel;
import vo.billvo.ChangeBillVO;

public class BillTools {
	
	private static GetCommodityInterface commodityInfo = new CommodityBL();
	private static GetCategoryInterface categoryInfo = new CategoryBL();
	private static GetUserInterface userInfo = new UserBL();

	/**
	 * 报溢/报损单据PO向VO的转换，其中库存数量为实时数据
	 * @param bill 
	 * @return
	 */
	public static ChangeBillVO getChangeBill(ChangeBillPO bill) {
		String[] headers = {"商品id", "商品名称", "库存数量", "实际数量"};
		int size = bill.getCommodityList().size();
		String[][] data = new String[size][4];
		for (int i = 0; i < size; i++) {
			ChangeItem item = bill.getCommodityList().get(i);
			data[i] = new String[]{item.getCommodityId(), "", ""+item.getOriginalValue(), ""+item.getChangedValue()};
			data[i][1] = categoryInfo.getCategory(commodityInfo.getCommodity(item.getCommodityId()).getCategoryId()).getName();
			if (bill.getState() != ChangeBillPO.PASS) data[i][2]= ""+commodityInfo.getCommodity(item.getCommodityId()).getAmount();
		}
		return new ChangeBillVO(bill.getDate(), bill.getTime(), bill.getId(), userInfo.getUser(bill.getOperator()).getName(), bill.getState(), bill.getFlag(), new MyTableModel(data, headers));
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
