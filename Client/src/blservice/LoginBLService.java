package blservice;

import vo.UserVO;

/**
 * ��¼ʱʹ�õĽӿ�
 * @author Ǯ��Ե
 */
public interface LoginBLService {
	
	/**
	 * �����û������ID��������������֤
	 * @param id �û��ĵ�¼��
	 * @param password �û����������
	 * @return ��¼ʧ�ܷ���null���ɹ����ض�Ӧ��UserVO
	 */
	public UserVO getUser(String id, String password);
	
}
