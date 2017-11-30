package bl_stub;

import blservice.billblservice.SaleBillBLService;
import vo.billvo.SaleBillVO;

public class SaleBillBL_stub implements SaleBillBLService {

	@Override
	public String getNewId() {
		//将首字母作为参数传给数据层
		return "XSD-20171128-00001";
	}

	@Override
	public boolean deleteBill(String id) {
		System.out.println(id + " has been deleted");
		return true;
	}

	@Override
	public boolean saveBill(SaleBillVO bill) {
		System.out.println(bill.getAllId() + " has been deleted");
		return true;
	}

	@Override
	public boolean updateBill(SaleBillVO bill) {
		System.out.println(bill.getAllId() + " has been deleted");
		return true;
	}

	@Override
	public SaleBillVO getBill(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
