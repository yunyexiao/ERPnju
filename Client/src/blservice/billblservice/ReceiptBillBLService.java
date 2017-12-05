package blservice.billblservice;

import vo.billvo.ReceiptBillVO;

public interface ReceiptBillBLService extends BillBLService {

	public boolean saveBill(ReceiptBillVO bill);
	
	public boolean updateBill(ReceiptBillVO bill);
	/**
	 * 这里会将PO转换成VO
	 * @param id 完整的id标号
	 * @return 现金费用单据的VO类
	 */
	public ReceiptBillVO getBill(String id);
}
