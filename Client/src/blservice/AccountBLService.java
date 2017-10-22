package blservice;

import java.util.ArrayList;
import vo.AccountVO;

public interface AccountBLService {

	public boolean addAccount(String name, String number, int money);
	public ArrayList<AccountVO> getAccountInfo(String name);
	public ArrayList<AccountVO> getAccountInfo();
	public boolean updateAccount();
	public boolean deleteAccount(AccountVO account);
	
}
