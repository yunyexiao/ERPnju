package businesslogic;

import javax.swing.table.DefaultTableModel;

import blservice.LoginBLService;
import blservice.UserBLService;
import vo.UserType;
import vo.UserVO;

public class UserBL_stub implements UserBLService, LoginBLService {

	@Override
	public boolean delete(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DefaultTableModel search(String type, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DefaultTableModel update() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * ��1��5�ֱ�˳�򷵻�5�����
	 */
	@Override
	public UserVO getUser(String id, String password) {
		if ("1".equals(id)) return new UserVO("��", UserType.KEEPER);
		else if ("2".equals(id)) return new UserVO("��", UserType.SALESMAN);
		else if ("3".equals(id)) return new UserVO("����", UserType.ACCOUNTANT);
		else if ("4".equals(id)) return new UserVO("��ۼ���", UserType.GM);
		else if ("5".equals(id)) return new UserVO("�û�������", UserType.ADMIN);
		else return null;
	}

	@Override
	public boolean add(UserVO user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean change(UserVO user) {
		// TODO Auto-generated method stub
		return false;
	}

}
