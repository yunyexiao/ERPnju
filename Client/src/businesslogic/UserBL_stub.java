package businesslogic;

import blservice.LoginBLService;
import blservice.UserBLService;
import presentation.component.MyTableModel;
import vo.UserType;
import vo.UserVO;

public class UserBL_stub implements UserBLService, LoginBLService {

	@Override
	public boolean delete(String id) {
		System.out.println("用户信息已成功删除");
		return true;
	}

	@Override
	public MyTableModel search(String type, String key) {
		String[] attributes={"用户编号","姓名","职务","性别","年龄","联系电话"};
		String[][] info={{"001","Van","库存管理人员","男","34","无"}};
		System.out.println("显示搜索的用户信息");
		return new MyTableModel(info, attributes);
	}

	@Override
	public MyTableModel update() {
		String[] attributes={"用户编号","姓名","职务","性别","年龄","联系电话"};
		String[][] info={{"001","Van","库存管理人员","男","34","无"},
				{"002","Bili","总经理","男","50","无"}};
		System.out.println("用户信息已成功更新");
		return new MyTableModel(info, attributes);
	}

	/**
	 * 从1到5分别按顺序返回5种身份
	 */
	@Override
	public UserVO getUser(String id, String password) {
		if ("1".equals(id)) return new UserVO("他", UserType.KEEPER, id, "男", "", 91);
		else if ("2".equals(id)) return new UserVO("蛤", UserType.SALESMAN, id, "男", "", 91);
		else if ("3".equals(id)) return new UserVO("长者", UserType.ACCOUNTANT, id, "男", "", 91);
		else if ("4".equals(id)) return new UserVO("香港记者", UserType.GM, id, "男", "", 91);
		else if ("5".equals(id)) return new UserVO("用户不存在", UserType.ADMIN, id, "男", "", 91);
		else return null;
	}

	@Override
	public boolean add(UserVO user) {
		System.out.println("用户信息已成功添加");
		return true;
	}

	@Override
	public boolean change(UserVO user) {
		System.out.println("用户信息已更改");
		return true;
	}

	@Override
	public String getNewId() {
		return "0002";
	}

}
