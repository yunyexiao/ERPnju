package blservice.billblservice;

import vo.billvo.CashCostBillVO;

public interface CashCostBillBLService extends BillBLService {
	
	/**
	 * 将单据保存到数据库（和保存单据不van全一致）
	 * @return 是否保存成功
	 */
	public boolean saveBill(CashCostBillVO bill);
	/**
	 * 这里会将PO转换成VO
	 * @param id 完整的id标号
	 * @return 现金费用单据的VO类
	 */
	public CashCostBillVO getBill(String id);

}