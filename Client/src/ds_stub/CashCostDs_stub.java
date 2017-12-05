package ds_stub;

import dataservice.CashCostBillDataService;
import po.billpo.CashCostBillPO;

public class CashCostDs_stub implements CashCostBillDataService {

	@Override
	public boolean saveBill(CashCostBillPO bill) {
		return true;
	}

	@Override
	public boolean deleteBill(String billid) {
		return true;
	}

	@Override
	public String getNewId() {
		return "XJFYD-20171203-00001";
	}

	@Override
	public CashCostBillPO getBillById(String id) {
		return null;
	}

}
