package blservice.billblservice;

import presentation.component.MyTableModel;
import vo.billvo.SalesBillVO;

public interface SalesBillBLService extends BillBLService {

	/**
	 * �����ݱ��浽���ݿ⣨�ͱ��浥�ݲ�vanȫһ�£�
	 * @return �Ƿ񱣴�ɹ�
	 */
	public boolean saveBill(SalesBillVO bill);
	/**
	 * �������ݿ��е�Bill����
	 * @return
	 */
	public boolean updateBill(SalesBillVO bill);
	/**
	 * ����ὫPOת����VO
	 * @param id ������id���
	 * @return ���۵��ݵ�VO��
	 */
	public SalesBillVO getBill(String id);
	
	public MyTableModel search(String type, String key);
	
	public MyTableModel getBillsByDate(String from, String to);
	
	public MyTableModel getFinishedBills();
	
	public MyTableModel getFinishedBills(String customerId);
	
}
