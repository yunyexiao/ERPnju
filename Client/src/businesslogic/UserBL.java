package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import blservice.UserBLService;
import dataservice.UserDataService;
import po.UserPO;
import presentation.component.MyTableModel;
import rmi.RemoteHelper;
import vo.UserVO;

public class UserBL implements UserBLService{
	
	private UserDataService userDataService = RemoteHelper.getInstance().getUserDataService();

	@Override
	public boolean delete(String id) {
		try {
			return userDataService.delete(id);
		} catch (Exception e) {
			return false;
		}
	}

	@Override//暂时只实现按ID查找功能
	public MyTableModel search(String type, String key) {
		try {
			String[] columnNames = {"用户ID", "用户名", "性别", "年龄", "电话号码", "用户类别"};
			UserPO userPO = userDataService.findById(key);
			String[][] data = {{userPO.getUserId(), userPO.getUserName(), userPO.getUserSex(),
				Integer.toString(userPO.getUserAge()), userPO.getUserTelNumber(), Integer.toString(userPO.getUsertype())}};
			MyTableModel searchTable = new MyTableModel (data, columnNames);
			return searchTable;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public MyTableModel update() {
		try {
			ArrayList<UserPO> list=userDataService.getAllUser();
			String[] columnNames = {"用户ID", "用户名", "性别", "年龄", "电话号码", "用户类别"};
			String[][] data = new String [list.size()][6];
			for (int i=0; i<list.size(); i++) {
				data[i][0] = list.get(i).getUserId();
				data[i][1] = list.get(i).getUserName();
				data[i][2] = list.get(i).getUserSex();
				data[i][3] = Integer.toString(list.get(i).getUserAge());
				data[i][4] = list.get(i).getUserTelNumber();
				data[i][5] = Integer.toString(list.get(i).getUsertype());
			}
			MyTableModel searchTable = new MyTableModel (data, columnNames);
			return searchTable;
		} catch (Exception e) {
			return null;
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

}
