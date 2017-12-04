package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import blservice.AccountBLService;
import businesslogic.inter.GetAccountInterface;
import dataservice.AccountDataService;
import dataservice.CustomerDataService;
import po.AccountPO;
import po.CustomerPO;
import po.UserPO;
import presentation.component.MyTableModel;
import rmi.Rmi;
import vo.AccountVO;
import vo.UserType;
import vo.UserVO;

public class AccountBL implements AccountBLService, GetAccountInterface {

	private AccountDataService accountDataService = Rmi.getRemote(AccountDataService.class);
	private String[] tableHeader = {"银行账号", "账户名称", "余额"};
	
	private String[] getLine(AccountPO account) {
		return new String[] {
				account.getId(), 
				account.getName(),
				Double.toString(account.getMoney())
		};
	}
	
	@Override
	public String getNewId() {
		try {
			return accountDataService.getNewId();
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean delete(String id) {
		try {
			return accountDataService.delete(id);
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public MyTableModel search(String type, String key) {
		try {
			ArrayList<AccountPO> list = null;
			list = accountDataService.getUsersBy("SAID", key, true);
			String[][] data = new String [list.size()][tableHeader.length];
			for (int i = 0; i < list.size(); i++) {
				data[i] = getLine(list.get(i));
			}
			MyTableModel searchTable = new MyTableModel (data, tableHeader);
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
			return accountDataService.add(accountPO);
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean change(AccountVO account) {
		try {
			AccountPO accountPO = account.toPO();
			return accountDataService.update(accountPO);
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
