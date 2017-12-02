package dataservice;

import po.billpo.ChangeBillPO;

public interface ChangeBillDataService {

	public ChangeBillPO getBillById(String id);
	
	public boolean saveBill(ChangeBillPO bill);
	
	public boolean deleteBill(String id);
	/**
	 * 返回当天单据的最新编号，即完整ID的最后一部分！不是全部！
	 * @param isOver 判断是否为报溢单-true
	 * @return
	 */
	public String getNewId(boolean isOver);
	
}
