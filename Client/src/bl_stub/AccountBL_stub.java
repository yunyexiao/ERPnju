package bl_stub;

import blservice.AccountBLService;
import presentation.component.MyTableModel;
import vo.AccountVO;

public class AccountBL_stub implements AccountBLService {

	@Override
	public boolean delete(String id) {
		System.out.println("账户信息已成功删除");
		return true;
	}

	@Override
	public MyTableModel search(String type, String key) {
		String[] attributes={"银行账号", "账户名称", "余额"};
		String[][] info={{"111111111", "马云", "9999999999999"}};
		System.out.println("显示搜索的账户信息");
		return new MyTableModel(info, attributes);
	}

	@Override
	public MyTableModel update() {
		String[] attributes={"银行账号", "账户名称", "余额"};
		String[][] info={{"111111111", "马云", "9999999999999"}};
		System.out.println("账户信息已成功更新");
		return new MyTableModel(info, attributes);
	}

	@Override
	public boolean add(AccountVO account) {
		System.out.println("账户信息已成功添加");
		return true;
	}

	@Override
	public boolean change(AccountVO account) {
		System.out.println("账户信息已更改");
		return true;
	}

	@Override
	public String getNewId() {
		return "0003";
	}

}
