package presentation;

import vo.UserVO;

public interface PanelInterface {
	
	//�������л��ر�Panelʱ������ɹ��رգ�����true,���򷵻�false
	public boolean close();
	
	//���������ڴ��ݵ�VO�����ʼ������ϵ����ݣ�ע���빹�캯������
	public void init(UserVO user);
	
}
