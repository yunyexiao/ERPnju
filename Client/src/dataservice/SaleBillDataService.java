package dataservice;

import po.billpo.SaleBillPO;

public interface SaleBillDataService {

	/**
	 * 每一种单据都对应一个BillData
	 * 这里没有区分新增和更新！！！，或者区分一下？
	 * @param bill
	 * @return
	 */
	public boolean saveBill(SaleBillPO bill);
	/**
	 * 根据编号删除一张单据
	 * @param billid
	 * @return
	 */
	public boolean deleteBill(String billid);
	/**
	 * 获得销售单据的新编号
	 * @return
	 */
	public String getNewId();
	
	public SaleBillPO getBillById(String id);
}
