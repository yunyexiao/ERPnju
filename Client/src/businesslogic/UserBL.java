package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import blservice.UserBLService;
import blservice.infoservice.GetUserInterface;
import businesslogic.inter.AddLogInterface;
import dataservice.UserDataService;
import ds_stub.UserDs_stub;
import po.UserPO;
import rmi.Rmi;
import vo.MyTableModel;
import vo.UserType;
import vo.UserVO;

public class UserBL implements UserBLService, GetUserInterface{
	
	private UserDataService userDataService = Rmi.flag ? Rmi.getRemote(UserDataService.class) : new UserDs_stub();//
	private String[] tableHeader = {"�û�ID", "�û���", "�û����", "�û�Ȩ��", "�û�����", "�Ա�", "����", "�绰����"};
	private AddLogInterface addLog = new LogBL();
	
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
			if (userDataService.delete(id)) {
				addLog.add("ɾ���û�", "�û�ID��" + id);
				return true;
			} else return false;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public MyTableModel search(String type, String key) {
		ArrayList<UserPO> list = null;
		try {
			if ("���������".equals(type)) {
				list = userDataService.getUsersBy("SUID", key, true);
			} else if ("����������".equals(type)) {
				list = userDataService.getUsersBy("SUName", key, true);
			}
			String[][] data = new String [list.size()][tableHeader.length];
			for (int i = 0; i < list.size(); i++) {
				data[i] = getLine(list.get(i));
			}
			return new MyTableModel (data, tableHeader);
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
			if (userDataService.add(userPO)) {
				addLog.add("�����û�", "����"+user.getType().getName()+":"+user.getName());
				return true;
			} else return false;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean change(UserVO user) {
		try {
			UserPO userPO = user.toPO();
			if (userDataService.update(userPO)) {
				addLog.add("�޸��û�", "�޸ĵ��û�ID��"+user.getId());
				return true;
			} else return false;
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
