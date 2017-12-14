package ds_stub;

import java.util.ArrayList;

import dataservice.BillDataService;
import po.UserPO;
import po.billpo.BillPO;
import po.billpo.ChangeBillPO;
import po.billpo.ChangeItem;

public class BillDs_stub implements BillDataService {

	ArrayList<BillPO> list = new ArrayList<BillPO>();
	
	@Override
	public ArrayList<BillPO> getBillList(UserPO user) {
		ArrayList<ChangeItem> commodityList = new ArrayList<ChangeItem>();
		if (user.getUsertype() == UserPO.UserType.STORE_KEEPER) {
			commodityList.add(new ChangeItem("000002", 100, 80));
			commodityList.add(new ChangeItem("000003", 20, 10));
			list.add(new ChangeBillPO("20171103", "16:19:31","00001","0001", BillPO.SAVED, false, commodityList));
			list.add(new ChangeBillPO("20171103", "12:19:31","00002","0001", BillPO.NOTPASS, false, commodityList));
			commodityList.clear();
			commodityList.add(new ChangeItem("000002", 60, 80));
			list.add(new ChangeBillPO("20171105", "11:19:31","00001","0001", BillPO.SAVED, true, commodityList));
		}
		return list;
	}
	
	@Override
	public ChangeBillPO getChangeBill(String id) {
		ArrayList<ChangeItem> commodityList = new ArrayList<ChangeItem>();
		commodityList.add(new ChangeItem("000002", 100, 80));
		commodityList.add(new ChangeItem("000003", 20, 10));
		return new ChangeBillPO("20171103", "16:19:31","00001","0001", BillPO.SAVED, false, commodityList);
	}

}
