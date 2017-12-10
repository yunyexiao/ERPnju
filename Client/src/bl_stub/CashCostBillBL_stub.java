package bl_stub;

import blservice.billblservice.CashCostBillBLService;
import vo.billvo.CashCostBillVO;

public class CashCostBillBL_stub implements CashCostBillBLService {

	@Override
	public String getNewId() {
		return "XJFYD-20171204-00001";
	}

	@Override
	public boolean deleteBill(String id) {
		System.out.println(id + " has been deleted");
	    return true;
	}

	@Override
	public boolean updateBill(CashCostBillVO bill) {
		System.out.println(bill.getId() + " has been updated");
	    return true;
	}

	@Override
	public boolean saveBill(CashCostBillVO bill) {
        System.out.println(bill.getId() + " has been saved");
        return true;
	}

	@Override
	public CashCostBillVO getBill(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
