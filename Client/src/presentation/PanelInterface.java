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
	
	//���������е�JPanel���������
	public JPanel getPanel();
	
}
