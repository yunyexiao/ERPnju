package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import blservice.UserBLService;
import businesslogic.inter.GetUserInterface;
import dataservice.UserDataService;
import po.UserPO;
import presentation.component.MyTableModel;
import rmi.Rmi;
import vo.UserType;
import vo.UserVO;

public class UserBL implements UserBLService, GetUserInterface{
	
	private UserDataService userDataService = Rmi.getRemote(UserDataService.class);
	private String[] tableHeader = {"用户ID", "用户名", "用户类别", "用户权限", "用户密码", "性别", "年龄", "电话号码"};

	private String[] getLine(UserPO user) {
		return new String[]{
			  user.getUserId()
			, user.getUserName()
			, UserType.getType(user.getUsertype()).getName()
			, user.getRankName()
			, user.getUserPwd()
			, user.getUserSex()
			, Integer.toString(user.getUserAge())
			, user.getUserTelNumber()
		};
	}
	
	@Override
	public boolean delete(String id) {
		try {
			return userDataService.delete(id);
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public MyTableModel search(String type, String key) {
		ArrayList<UserPO> list = null;
		try {
			if ("按编号搜索".equals(type)) {
				list = userDataService.getUsersBy("SUID", key, true);
			} else if ("按名称搜索".equals(type)) {
				list = userDataService.getUsersBy("SUName", key, true);
			}
			String[][] data = new String [list.size()][tableHeader.length];
			for (int i = 0; i < list.size(); i++) {
				data[i] = getLine(list.get(i));
			}
			MyTableModel searchTable = new MyTableModel (data, tableHeader);
			return searchTable;
		} catch (Exception e) {
			return new MyTableModel (new String[][]{{}}, tableHeader);
		}
	}

	@Override
	public MyTableModel update() {
		try {
			ArrayList<UserPO> list = userDataService.getAllUser();
			String[][] data = new String [list.size()][tableHeader.length];
			for (int i = 0; i < list.size(); i++) {
				data[i] = getLine(list.get(i));
			}
			MyTableModel searchTable = new MyTableModel (data, tableHeader);
			return searchTable;
		} catch (Exception e) {
			return new MyTableModel (new String[][]{{}}, tableHeader);
		}
	}

	@Override
	public boolean add(UserVO user) {
		try {
			UserPO userPO = user.toPO();
			return userDataService.add(userPO);
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean change(UserVO user) {
		try {
			UserPO userPO = user.toPO();
			return userDataService.update(userPO);
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public String getNewId() {
		try {
			return userDataService.getNewId();
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public UserVO getUser(String id) {
		UserPO user = null;
		try {
			user = userDataService.findById(id);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if (user != null) {
			return new UserVO(
					user.getUserName(),
					user.getUserPwd(),
					UserType.getType(user.getUsertype()),
					user.getUserRank(),
					user.getUserId(),
					user.getUserSex(),
					user.getUserTelNumber(),
					user.getUserAge()); 
		}
		return null;
	}

}
