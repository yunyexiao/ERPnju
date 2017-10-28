package blservice;

import vo.BillVO;

public interface BillBLService {

	/**
	 * 无参数表示新建空单据
	 * @return 一份空白的单据，编号从服务器获得 
	 */
	public BillVO getTableModel();
	/**
	 * 获得服务器中编号为ID的单据对象
	 * @param id 需要查找的单据编号
	 * @return 从服务器查找到的单据
	 */
	public BillVO getTableModel(String id);
	/**
	 * 保存一张单据
	 * @param bill 需要保存到服务器的单据对象
	 * @return 保存成功/失败信息
	 */
	public boolean saveBill(BillVO bill);
}
