package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import blservice.AccountBLService;
import dataservice.AccountDataService;
import dataservice.CustomerDataService;
import po.AccountPO;
import po.CustomerPO;
import presentation.component.MyTableModel;
import rmi.Rmi;
import vo.AccountVO;

public class AccountBL implements AccountBLService{

	private AccountDataService accountDataService = Rmi.getRemote(AccountDataService.class);
	
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
			String[] columnNames = {"银行账号", "账户名称", "余额"};
			AccountPO accountPO = accountDataService.findById(key);
			String[][] data = {{accountPO.getId(), accountPO.getName(), Double.toString(accountPO.getMoney())}};
			MyTableModel searchTable = new MyTableModel (data, columnNames);
			return searchTable;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public MyTableModel update() {
		try {
			ArrayList<AccountPO> list = accountDataService.getAllAccount();
			String[] columnNames = {"银行账号", "账户名称", "余额"};
			String[][] data = new String [list.size()][3];
			for (int i = 0; i < 3; i++) {
				data[i][0] = list.get(i).getId();
				data[i][1] = list.get(i).getName();
				data[i][2] = Double.toString(list.get(i).getMoney());
			}
			MyTableModel updateTable = new MyTableModel (data, columnNames);
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

}
