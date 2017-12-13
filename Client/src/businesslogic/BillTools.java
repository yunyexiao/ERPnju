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
	 * ����/���𵥾�PO��VO��ת�������п������Ϊʵʱ����
	 * @param bill 
	 * @return
	 */
	public static ChangeBillVO getChangeBill(ChangeBillPO bill) {
		String[] headers = {"��Ʒid", "��Ʒ����", "�������", "ʵ������"};
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
	 * ����billPO���������ͻ�õ��ݵ���������
	 * @param bill billPO����
	 * @return
	 */
	public static String getBillName(BillPO bill) {
		if (bill instanceof ChangeBillPO) return ((ChangeBillPO)bill).getFlag() ? "���絥":"����";
		else if (bill instanceof SalesBillPO) return "���۵�";
		else if (bill instanceof SalesReturnBillPO) return "�����˻���";
		else if (bill instanceof PurchaseBillPO) return "������";
		else if (bill instanceof PurchaseReturnBillPO) return "�����˻���";
		else if (bill instanceof PaymentBillPO) return "���";
		else if (bill instanceof ReceiptBillPO) return "�տ";
		else if (bill instanceof CashCostBillPO) return "�ֽ���õ�";
		return "δ֪����";
	}
}
