package blservice.billblservice;

public interface BillCreateBLService {

	/**
	 * 返回完整的Id[XXX-yyyyMMdd-00000]
	 * @return 下一条单据的Id
	 */
	public String getNewId();
	/**
	 * 删除一张单据（已提交状态的单据不能删除）
	 * @param id 需要删除的单据id[应该是一个完整的id]
	 * @return 是否删除成功
	 */
	public boolean deleteBill(String id);
}
