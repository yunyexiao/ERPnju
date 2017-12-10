package ds_stub;

import dataservice.CashCostBillDataService;
import po.billpo.CashCostBillPO;

public class CashCostBillDs_stub implements CashCostBillDataService {

	@Override
	public boolean saveBill(CashCostBillPO bill) {
        System.out.println("cashcost bill saved in database: " + bill.getId());
		return true;
	}

	@Override
	public boolean deleteBill(String billid) {
        System.out.println("cashcost bill deleted in database: " + billid);
		return true;
	}

	@Override
	public String getNewId() {
		return "XJFYD-20171210-00001";
	}

	@Override
	public CashCostBillPO getBillById(String id) {
		return null;
	}

}
