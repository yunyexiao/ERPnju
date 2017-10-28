package businesslogic;

import javax.swing.table.DefaultTableModel;

import blservice.BillBLService;
import vo.BillVO;
import vo.UserVO;

public class BillBL_stub implements BillBLService {
	private UserVO user;
	
	public BillBL_stub (UserVO user) {
		this.user = user;
	}

	@Override
	public BillVO getTableModel() {
		String[] attributes={"��Ʒ���","����","�ͺ�","����","�ֿ�","����","���","��ע"};
		String[][] info={{"","","","","","","",""}};
		DefaultTableModel model = new DefaultTableModel(info, attributes);
		return new BillVO("JHXSD-201710280001",user.getName(),"","", model, "", "");
	}

	@Override
	public BillVO getTableModel(String id) {
		String[] attributes={"��Ʒ���","����","�ͺ�","����","�ֿ�","����","���","��ע"};
		String[][] info={{"001","xx","yy","20","3","3","40","xx"}};
		DefaultTableModel model = new DefaultTableModel(info, attributes);
		return new BillVO("JHXSD-201710270001","Van","","", model, "0", "remark");
	}

	@Override
	public boolean saveBill(BillVO bill) {
		return true;
	}

}
