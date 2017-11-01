package blservice;

import vo.UserVO;

public interface UserBLService extends DataBLService {

	/**
	 * ����һ���ͻ���Ϣ�ļ�¼
	 * @param customer �ͻ�VO
	 * @return �Ƿ�ɹ����
	 */
	public boolean add(UserVO user);
	/**
	 * ����һ���ͻ���Ϣ
	 * @param customer �ͻ�VO
	 * @return ����Ѵ��ڲ��ҳɹ����ķ���true
	 */
	public boolean change(UserVO user);
	
}
