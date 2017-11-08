package businesslogic;

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
	public String[][] getBillInfo() {
		String[][] info={
				{"2017-11-03 15:34:22","JHXSD-201710270001","�������۵�","�ݸ�"},
				{"2017-11-03 16:19:31","JHTHD-201710270001","�����˻���","������"},
				{"2017-11-03 19:51:42","JHXSD-201710270002","�������۵�","������"}};
		return info;
	}

	@Override
	public boolean deleteBill(String id) {
		System.out.println("�����ѳɹ�ɾ��");
		return true;
	}

}