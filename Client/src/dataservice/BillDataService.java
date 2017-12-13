package dataservice;

import java.util.ArrayList;

import po.UserPO;
import po.billpo.BillPO;
import po.billpo.ChangeBillPO;

public interface BillDataService {
	
	public ArrayList<BillPO> getBillList(UserPO user);
	
	public ChangeBillPO getChangeBill(String id);
}
