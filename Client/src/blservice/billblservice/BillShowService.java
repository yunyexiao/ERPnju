package blservice.billblservice;

import presentation.component.MyTableModel;
import vo.UserVO;

public interface BillShowService {

	public MyTableModel getBillTable(UserVO user);
}
