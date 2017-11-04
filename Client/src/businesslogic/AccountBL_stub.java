package businesslogic;

import javax.swing.table.DefaultTableModel;

import blservice.AccountBLService;
import vo.AccountVO;

public class AccountBL_stub implements AccountBLService {

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

	@Override
	public boolean add(AccountVO account) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean change(AccountVO account) {
		// TODO Auto-generated method stub
		return false;
	}

}
