package blservice.billblservice;

import vo.billvo.PaymentBillVO;

public interface PaymentBillBLService extends BillBLService {

	public String getNewId();

	public boolean deleteBill(String id);

	public boolean saveBill(PaymentBillVO bill);
	
	public boolean updateBill(PaymentBillVO bill);

	public PaymentBillVO getBill(String id);
}
