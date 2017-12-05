package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import blservice.AccountBLService;
import blservice.infoservice.GetAccountInterface;
import dataservice.AccountDataService;
import po.AccountPO;
import presentation.component.MyTableModel;
import rmi.Rmi;
import vo.AccountVO;

public class AccountBL implements AccountBLService, GetAccountInterface {

	private AccountDataService accountDataService = Rmi.getRemote(AccountDataService.class);
	private String[] tableHeader = {"“¯––’À∫≈", "’Àªß√˚≥∆", "”‡∂Ó"};
	
	private String[] getLine(AccountPO account) {
		return new String[] {
				account.getId(), 
				account.getName(),
				Double.toString(account.getMoney())
		};
	}
	
	@Override
	public String getNewId() {
		return null;
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
			if ("∞¥’À∫≈À—À˜".equals(type)) {
				list = accountDataService.getAccountsBy("SAID", key, true);
			}
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
