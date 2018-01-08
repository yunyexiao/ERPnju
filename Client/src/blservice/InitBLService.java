package blservice;

import vo.MyTableModel;

public interface InitBLService {

	/**
	 * ��ȡ��ǰ���׵����
	 * @return �����¼��û�е�ǰ�����򷵻�null
	 */
	public String getYear();
	/**
	 * ����ڳ��б�
	 * @return
	 */
	public String[] getInitInfo();
	/**
	 * ����ѡ�����׵���Ʒ��Ϣ��������Ϊnull�򷵻ص�ǰ��Ϣ
	 * @param year
	 * @return
	 */
	public MyTableModel getCommodityInfo(String year);
	
	public MyTableModel getCustomerInfo(String year);
	
	public MyTableModel getAccountInfo(String year);
	/**
	 * �ڳ���Ϣ��ʼ��
	 * @return
	 */
	public boolean initNewOne();
}
