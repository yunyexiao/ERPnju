package blservice.billblservice;

import vo.billvo.ReceiptBillVO;

public interface ReceiptBillBLService extends BillBLService {

	public String getNewId();

	public boolean deleteBill(String id);

	public boolean saveBill(ReceiptBillVO bill);
	
	public boolean updateBill(ReceiptBillVO bill);

	public ReceiptBillVO getBill(String id);
}
