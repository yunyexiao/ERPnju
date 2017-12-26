package presentation.billui;

import java.awt.event.ActionListener;

import presentation.PanelInterface;
import vo.UserVO;
import vo.billvo.BillVO;
import vo.billvo.CashCostBillVO;
import vo.billvo.ChangeBillVO;
import vo.billvo.PaymentBillVO;
import vo.billvo.PurchaseBillVO;
import vo.billvo.PurchaseReturnBillVO;
import vo.billvo.ReceiptBillVO;
import vo.billvo.SalesBillVO;
import vo.billvo.SalesReturnBillVO;

public class BillPanelHelper {
	public static UserVO user;
	public static ActionListener closeListener;

	/**
	 * ���ذ�����ť�����½����ݴ���
	 * @param name ������𣺸���������ĸ��д����CashCostBill
	 * @return
	 */
	public static PanelInterface create(String name) {
		switch (name) {
		case "CashCostBill" : return new BillPanel(closeListener, new CashCostBillPanel(user));
		case "ChangeBill" : return new BillPanel(closeListener, new ChangeBillPanel(user));
		case "PurchaseBill" : return new BillPanel(closeListener, new PurchaseBillPanel(user));
		case "PurchaseReturnBill" : return new BillPanel(closeListener, new PurchaseReturnBillPanel(user));
		case "ReceiptOrPaymentBill" : return new BillPanel(closeListener, new ReceiptOrPaymentBillPanel(user));
		case "SalesBill" : return new BillPanel(closeListener, new SalesBillPanel(user));
		case "SalesReturnBill" : return new BillPanel(closeListener, new SalesReturnBillPanel(user));
		default : return null;
		}
	}
	
	public static PanelInterface create(BillVO bill) {
		BillPanel billPanel = new BillPanel(closeListener, createInner(bill));
		if (bill.getState() == BillVO.PASS || bill.getState() == BillVO.COMMITED) billPanel.setEditable(false);
		return billPanel;
	}
	
	public static BillPanelInterface createInner(BillVO bill) {
		if (bill instanceof CashCostBillVO) return new CashCostBillPanel(user, (CashCostBillVO)bill);
		else if (bill instanceof ChangeBillVO) return new ChangeBillPanel(user, (ChangeBillVO)bill);
		else if (bill instanceof PurchaseBillVO) return new PurchaseBillPanel(user, (PurchaseBillVO)bill);
		else if (bill instanceof PurchaseReturnBillVO) return new PurchaseReturnBillPanel(user, (PurchaseReturnBillVO)bill);
		else if (bill instanceof ReceiptBillVO) return new ReceiptOrPaymentBillPanel(user, (ReceiptBillVO)bill);
		else if (bill instanceof PaymentBillVO) return new ReceiptOrPaymentBillPanel(user, (PaymentBillVO)bill);
		else if (bill instanceof SalesBillVO) return new SalesBillPanel(user, (SalesBillVO)bill);
		else if (bill instanceof SalesReturnBillVO) return new SalesReturnBillPanel(user, (SalesReturnBillVO)bill);
		else return null;
	}
}
