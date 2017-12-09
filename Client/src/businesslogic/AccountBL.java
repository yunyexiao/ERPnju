package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import blservice.AccountBLService;
import blservice.infoservice.GetAccountInterface;
import businesslogic.inter.AddLogInterface;
import dataservice.AccountDataService;
import ds_stub.AccountDs_stub;
import po.AccountPO;
import presentation.component.MyTableModel;
import rmi.Rmi;
import vo.AccountVO;
import vo.UserVO;

public class AccountBL implements AccountBLService, GetAccountInterface {

	private AccountDataService accountDataService = Rmi.flag ? Rmi.getRemote(AccountDataService.class) : new AccountDs_stub();
	private AddLogInterface addLog;
	private String[] tableHeader = {"�����˺�", "�˻�����", "���"};
	private int userRank;
	
	/**
	 * ��ʹ��GetAccountInterface�Ĺ��췽��
	 */
	public AccountBL() {
		
	}
	
	public AccountBL(UserVO user) {
		userRank = user.getRank();
		addLog = new LogBL(user);
	}
	
	private String[] getLine(AccountPO account) {
		return new String[] {
				account.getId(), 
				account.getName(),
				userRank==1?Double.toString(account.getMoney()):"****"
		};
	}
	
	@Override
	public String getNewId() {
		return "";
	}

	@Override
	public boolean delete(String id) {
		try {
			if (accountDataService.delete(id)) {
				addLog.add("ɾ���˻�", "ɾ�����˻�����Ϊ��"+id);
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public MyTableModel search(String type, String key) {
		try {
			ArrayList<AccountPO> list = null;
			if ("���˺�����".equals(type)) {
				list = accountDataService.getAccountsBy("SAID", key, true);
			}
			String[][] data = new String [list.size()][tableHeader.length];
			for (int i = 0; i < list.size(); i++) {
				data[i] = getLine(list.get(i));
			}
			MyTableModel searchTable = new MyTableModel (data, tableHeader);
			if (addLog != null) addLog.add("�����˻�", "��ѯ������" + type + "	��ѯ�ؼ��ʣ�"+ key);
			return searchTable;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public MyTableModel update() {
		try {
			ArrayList<AccountPO> list = accountDataService.getAllAccount();
			String[][] data = new String [list.size()][tableHeader.length];
			for (int i = 0; i < list.size(); i++) {
				data[i] = getLine(list.get(i));
			}
			MyTableModel updateTable = new MyTableModel (data, tableHeader);
			return updateTable;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean add(AccountVO account) {
		try {
			AccountPO accountPO = account.toPO();
			if (accountDataService.add(accountPO)) {
				addLog.add("�����˻�", "���˻����ţ�"+account.getNumber());
				return true;
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean change(AccountVO account) {
		try {
			AccountPO accountPO = account.toPO();
			if (accountDataService.update(accountPO)) {
				addLog.add("�޸��˻�", "�޸ĵ��˻�����Ϊ��"+account.getNumber());
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}
	@Override
	public AccountVO getAccount(String id) {
		AccountPO account = null;
		try {
			account = accountDataService.findById(id);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if (account != null) {
			return new AccountVO(
					account.getId(),
					account.getName(),
					account.getMoney()); 
		}
		return null;
	}
}
