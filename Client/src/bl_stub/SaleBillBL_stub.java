package bl_stub;

import blservice.billblservice.SalesBillBLService;
import presentation.component.MyTableModel;
import vo.billvo.SalesBillVO;

public class SaleBillBL_stub implements SalesBillBLService {

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
	public boolean saveBill(SalesBillVO bill) {
		System.out.println(bill.getAllId() + " has been saved");
		return true;
	}

	@Override
	public boolean updateBill(SalesBillVO bill) {
		System.out.println(bill.getAllId() + " has been updated");
		return true;
	}

	@Override
	public SalesBillVO getBill(String id) {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public MyTableModel getFinishedBills() {
        // TODO Auto-generated method stub
        return null;
    }

}
