package businesslogic;

import java.util.ArrayList;

import blservice.AccountBLService;
import vo.AccountVO;

public class AccountBL_stub implements AccountBLService {

	@Override
	public boolean addAccount(String name, String number, int money) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public ArrayList<AccountVO> getAccountInfo(String name) {
		// TODO Auto-generated method stub
		return new ArrayList<AccountVO>(){{add(new AccountVO(name, "", 100));}};
	}

	@Override
	public ArrayList<AccountVO> getAccountInfo() {
		// TODO Auto-generated method stub
		return new ArrayList<AccountVO>(){{add(new AccountVO("ICBC", "", 100));add(new AccountVO("Bank", "", 200));}};
	}

	@Override
	public boolean updateAccount() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean deleteAccount(AccountVO account) {
		// TODO Auto-generated method stub
		return true;
	}

}
