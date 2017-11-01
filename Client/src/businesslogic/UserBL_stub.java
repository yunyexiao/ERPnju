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
	 * 从1到5分别按顺序返回5种身份
	 */
	@Override
	public UserVO getUser(String id, String password) {
		if ("1".equals(id)) return new UserVO("他", UserType.KEEPER);
		else if ("2".equals(id)) return new UserVO("蛤", UserType.SALESMAN);
		else if ("3".equals(id)) return new UserVO("长者", UserType.ACCOUNTANT);
		else if ("4".equals(id)) return new UserVO("香港记者", UserType.GM);
		else if ("5".equals(id)) return new UserVO("用户不存在", UserType.ADMIN);
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
