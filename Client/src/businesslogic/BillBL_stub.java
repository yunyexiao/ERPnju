package businesslogic;

import javax.swing.table.DefaultTableModel;

import blservice.BillBLService;
import vo.BillVO;
import vo.UserType;
import vo.UserVO;

public class BillBL_stub implements BillBLService {
	private UserVO user;
	
	public BillBL_stub (UserVO user) {
		this.user = user;
	}

	@Override
	public BillVO getTableModel() {
		return new BillVO("JHXSD-201710280001",user.getName(),"","", null, "", "");
	}

	@Override
	public BillVO getTableModel(String id) {
		String[][] info={{"001","xx","yy","20","3","3","40","xx"}};
		return new BillVO("JHXSD-201710270001","Van","","", info, "0", "remark");
	}

	@Override
	public boolean saveBill(BillVO bill) {
		return true;
	}

	@Override
	public DefaultTableModel getBillInfo() {
		String[] attributes;
		String[][] info;
		if (user.getType() == UserType.GM) {
			attributes = new String[]{"制定时间","单据编号","制定者", "单据类型"};
			info = new String[][]{
					{"2017-11-03 16:19:31","JHTHD-201710270001","蛤","进货退货单"}};
		} else {
			attributes = new String[]{"制定时间","单据编号","单据类型","状态"};
			info = new String[][]{
					{"2017-11-03 15:34:22","JHXSD-201710270001","进货销售单","草稿"},
					{"2017-11-03 16:19:31","JHTHD-201710270001","进货退货单","待审批"},
					{"2017-11-03 19:51:42","JHXSD-201710270002","进货销售单","已审批"}};
		}
		return new DefaultTableModel(info, attributes);
	}

	@Override
	public boolean deleteBill(String id) {
		System.out.println("单据已成功删除");
		return true;
	}

}
