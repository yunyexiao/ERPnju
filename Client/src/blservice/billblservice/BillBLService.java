package blservice.billblservice;

import presentation.component.MyTableModel;
import vo.UserVO;
import vo.billvo.ChangeBillVO;
import vo.billvo.PurchaseBillVO;
import vo.billvo.PurchaseReturnBillVO;
import vo.billvo.SalesBillVO;
import vo.billvo.SalesReturnBillVO;

public interface BillShowService {

	public MyTableModel getBillTable(UserVO user);
	
	public ChangeBillVO getChangeBill(String id);
	
	public PurchaseBillVO getPurchaseBill(String id);
	
	public PurchaseReturnBillVO getPurchaseReturnBill(String id);
	
	public SalesBillVO getSalesBill(String id);
	
	public SalesReturnBillVO getSalesReturnBill(String id);
}
