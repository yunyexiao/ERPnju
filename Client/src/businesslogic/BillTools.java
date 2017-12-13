package businesslogic;

import java.util.ArrayList;

import blservice.infoservice.GetCategoryInterface;
import blservice.infoservice.GetCommodityInterface;
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
import po.billpo.SalesReturnBillPO;
import po.billpo.TransferItem;
import presentation.component.MyTableModel;
import vo.billvo.CashCostBillVO;
import vo.billvo.ChangeBillVO;
import vo.billvo.PaymentBillVO;
import vo.billvo.ReceiptBillVO;

public class BillTools {
	
	private static GetCommodityInterface commodityInfo = new CommodityBL();
	private static GetCategoryInterface categoryInfo = new CategoryBL();
	private static GetUserInterface userInfo = new UserBL();

	/**
	 * ����/���𵥾�PO��VO��ת�������п������Ϊʵʱ����
	 * @param bill 
	 * @return
	 */
	public static ChangeBillVO toChangeBillVO(ChangeBillPO bill) {
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
	 * �ֽ���õ���PO��VO��ת��
	 * @param bill 
	 * @return
	 */
	public static CashCostBillVO toCashCostBillVO(CashCostBillPO bill) {
		String[] columnNames = {"��Ŀ��", "���", "��ע"};
        ArrayList<CashCostItem> items = bill.getCashcostList();
        String[][] data = new String[columnNames.length][items.size()];
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
        String[] columnNames = {"�����˻�", "ת�˽��", "��ע"};
        ArrayList<TransferItem> items = bill.getTransferList();
        String[][] data = new String[columnNames.length][items.size()];
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
        String[] columnNames = {"�����˻�", "ת�˽��", "��ע"};
        ArrayList<TransferItem> items = bill.getTransferList();
        String[][] data = new String[columnNames.length][items.size()];
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
