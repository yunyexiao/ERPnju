package blservice;

import presentation.component.MyTableModel;

public interface DataBLService {

    /**
     * ����һ���µĶ�Ӧ���ݵ�id
     * @return ��id
     */
    public String getNewId();
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
	public MyTableModel search(String type, String key);
	/**
	 * ���±��ģ��
	 * @return ���º�ı��ģ��
	 */
	public MyTableModel update();
	
}
