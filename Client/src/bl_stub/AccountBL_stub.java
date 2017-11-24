package bl_stub;

import blservice.AccountBLService;
import presentation.component.MyTableModel;
import vo.AccountVO;

public class AccountBL_stub implements AccountBLService {

	@Override
	public boolean delete(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MyTableModel search(String type, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyTableModel update() {
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

	@Override
	public String getNewId() {
		return "0003";
	}

}
