package businesslogic.inter;

import vo.UserVO;

public interface GetUserInterface {

	/**
	 * ��������ID�õ��û�VO����
	 * @param id ������id
	 * @return
	 */
	public UserVO getUser(String id);
}
