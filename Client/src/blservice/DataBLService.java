package blservice;

import javax.swing.table.DefaultTableModel;

public interface DataBLService {

	/**
	 * ����Ψһ��id���ɾ��һ����Ϣ
	 * @param id ��Ψһȷ����id���
	 * @return �ɹ�ɾ������true
	 */
	public boolean delete(String id);
	/**
	 * ȷ��������ʽ�͹ؼ��ʣ��õ��������
	 * @param type ������ʽ
	 * @param key �ؼ���
	 * @return ���ر���ģ��
	 */
	public DefaultTableModel search(String type, String key);
	/**
	 * ���±��ģ��
	 * @return ���º�ı��ģ��
	 */
	public DefaultTableModel update();
	
}
