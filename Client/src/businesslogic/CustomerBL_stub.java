package businesslogic;

import javax.swing.table.DefaultTableModel;

import blservice.CustomerBLService;
import vo.CustomerVO;

public class CustomerBL_stub implements CustomerBLService {

	@Override
	public boolean delete(String id) {
		System.out.println("客户信息已成功删除");
		return true;
	}

	@Override
	public DefaultTableModel search(String type, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DefaultTableModel update() {
		String[] attributes={"客户编号","姓名","分类","级别","应收额度","应收","应付","电话","邮编","电子邮箱","默认业务员"};
		String[][] info={{"JHS-001","清流","进货商","LV5","4000","0","0","","","","Van"},
				{"XSS-001","浊流","销售商","LV1","2000","100","400","","","","Van"}};
		return new DefaultTableModel(info, attributes);
	}

	@Override
	public boolean add(CustomerVO customer) {
		System.out.println("客户信息已成功添加");
		return true;
	}

}
