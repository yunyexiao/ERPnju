package blservice;

import vo.BillVO;

public interface BillBLService {
	
	/**
	 * ���ݹ����������棩��ȡ��ǰ�û��༭�ĵ�����Ϣ<br/>
	 * ����ʽ��������δͨ����-�ݸ�-�ύ����������-����ͨ����ÿ���а��ƶ�ʱ������
	 * @return ���ڱ����ʾ�Ķ�ά����{�ƶ�ʱ�䣬���ݱ�ţ��������ͣ�״̬}
	 */
	public String[][] getBillInfo();
	/**
	 * �����ƶ���ɾ��һ��δ��ɵĵ���</br>
	 * ֻ��ɾ���ݸ壨δ���ͨ�����ĵ��ݣ����ύ�ĺ����ͨ���Ĳ���ɾ��
	 * @param id ���ݵ�Ψһ���
	 * @return �Ƿ�ɹ�ɾ��
	 */
	public boolean deleteBill(String id);
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
