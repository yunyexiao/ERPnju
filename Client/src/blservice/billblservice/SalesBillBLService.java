package blservice.billblservice;

import presentation.component.MyTableModel;
import vo.billvo.SalesBillVO;

public interface SalesBillBLService extends BillCreateBLService {

	/**
	 * 将单据保存到数据库（和保存单据不van全一致）
	 * @return 是否保存成功
	 */
	public boolean saveBill(SalesBillVO bill);
	/**
	 * 更新数据库中的Bill数据
	 * @return
	 */
	public boolean updateBill(SalesBillVO bill);
	
	public MyTableModel search(String type, String key);
	
	public MyTableModel getBillsByDate(String from, String to);
	
	public MyTableModel getFinishedBills();
	
	public MyTableModel getFinishedBills(String customerId);
	
}
