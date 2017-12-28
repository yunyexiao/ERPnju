package blservice.billblservice;

import presentation.component.MyTableModel;
import vo.PromotionVO;
import vo.billvo.SalesBillVO;

public interface SalesBillBLService extends BillCreateBLService {

	/**
	 * �����ݱ��浽���ݿ⣨�ͱ��浥�ݲ�vanȫһ�£�
	 * @return �Ƿ񱣴�ɹ�
	 */
	public boolean saveBill(SalesBillVO bill);
	
	public MyTableModel search(String type, String key);
	
	public MyTableModel getBillsByDate(String from, String to);
	
	public MyTableModel getFinishedBills();
	
	public MyTableModel getFinishedBills(String customerId);
	
	public PromotionVO getBestPromotion(int type, MyTableModel goods, double sum);
	
}
