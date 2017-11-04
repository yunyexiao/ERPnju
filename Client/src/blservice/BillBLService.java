package blservice;

import vo.BillVO;

public interface BillBLService {
	
	/**
	 * 单据管理（即主界面）获取当前用户编辑的单据信息<br/>
	 * 排序方式：（审批未通过）-草稿-提交（待审批）-审批通过，每类中按制定时间排序
	 * @return 用于表格显示的二维数组{制定时间，单据编号，单据类型，状态}
	 */
	public String[][] getBillInfo();
	/**
	 * 单据制定者删除一个未完成的单据</br>
	 * 只能删除草稿（未审核通过）的单据，已提交的和审核通过的不能删除
	 * @param id 单据的唯一编号
	 * @return 是否成功删除
	 */
	public boolean deleteBill(String id);
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
