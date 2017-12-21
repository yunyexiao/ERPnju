package blservice.billblservice;

import presentation.component.MyTableModel;
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

public interface BillBLService {

	public MyTableModel getBillTable(UserVO user);
	
	public BillVO getBill(String id);
	
	public ChangeBillVO getChangeBill(String id);
	
	public PurchaseBillVO getPurchaseBill(String id);
	
	public PurchaseReturnBillVO getPurchaseReturnBill(String id);
	
	public SalesBillVO getSalesBill(String id);
	
	public SalesReturnBillVO getSalesReturnBill(String id);
	
	public CashCostBillVO getCashCostBill(String id);
	
	public PaymentBillVO getPaymentBill(String id);
	
	public ReceiptBillVO getReceiptBill(String id);
}
