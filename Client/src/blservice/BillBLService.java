package blservice;

import vo.BillVO;

public interface BillBLService {

	/**
	 * �޲�����ʾ�½��յ���
	 * @return һ�ݿհ׵ĵ��ݣ���Ŵӷ�������� 
	 */
	public BillVO getTableModel();
	/**
	 * ��÷������б��ΪID�ĵ��ݶ���
	 * @param id ��Ҫ���ҵĵ��ݱ��
	 * @return �ӷ��������ҵ��ĵ���
	 */
	public BillVO getTableModel(String id);
	/**
	 * ����һ�ŵ���
	 * @param bill ��Ҫ���浽�������ĵ��ݶ���
	 * @return ����ɹ�/ʧ����Ϣ
	 */
	public boolean saveBill(BillVO bill);
}
