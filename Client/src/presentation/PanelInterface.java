package presentation;

import javax.swing.JPanel;

import vo.UserVO;

public interface PanelInterface {
	
	/**
	 * Panel�ر�����ʱ����closeȷ���˳��������
	 * @return ȷ���˳�����true,����false(����׶�Ĭ��Ϊtrue)
	 * @author Ǯ��Ե
	 */
	public boolean close();
	
	//���������ڴ��ݵ�VO�����ʼ������ϵ����ݣ�ע���빹�캯������
	public void init(UserVO user);
	
	//���������е�JPanel���������
	public JPanel getPanel();
	
}
