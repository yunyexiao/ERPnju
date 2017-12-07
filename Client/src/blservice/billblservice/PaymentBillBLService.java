package blservice.billblservice;

import vo.billvo.PaymentBillVO;

public interface PaymentBillBLService extends BillBLService {
	
	public boolean saveBill(PaymentBillVO bill);
	
	public boolean updateBill(PaymentBillVO bill);
	/**
	 * 这里会将PO转换成VO
	 * @param id 完整的id标号
	 * @return 现金费用单据的VO类
	 */
	public PaymentBillVO getBill(String id);
}
