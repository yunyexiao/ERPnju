package blservice.billblservice;

import presentation.component.MyTableModel;
import vo.UserVO;
import vo.billvo.ChangeBillVO;

public interface BillShowService {

	public MyTableModel getBillTable(UserVO user);
	
	public ChangeBillVO getChangeBill(String id);
	
}
