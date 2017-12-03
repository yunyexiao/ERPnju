package blservice.billblservice;

import vo.billvo.ChangeBillVO;

public interface ChangeBillBLService extends BillBLService {

	/**
	 * 将单据保存到数据库（和保存单据不van全一致）
	 * @return 是否保存成功
	 */
	public boolean saveBill(ChangeBillVO bill);
	/**
	 * 更新数据库中的Bill数据
	 * @return
	public boolean updateBill(ChangeBillVO bill);
	 */
	/**
	 * 这里会将PO转换成VO
	 * @param id 完整的id标号
	 * @return 销售单据的VO类
	 */
	public ChangeBillVO getBill(String id);
}
