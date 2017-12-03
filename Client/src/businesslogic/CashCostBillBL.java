package businesslogic;

import java.util.ArrayList;

import blservice.billblservice.CashCostBillBLService;
import dataservice.CashCostBillDataService;
import ds_stub.CashCostDs_stub;
import po.billpo.CashCostBillPO;
import po.billpo.CashCostItem;
import presentation.component.MyTableModel;
import vo.billvo.CashCostBillVO;

public class CashCostBillBL implements CashCostBillBLService {

	private CashCostBillDataService cashCostBillDataService;
	
	public CashCostBillBL() {
		cashCostBillDataService = new CashCostDs_stub();//Rmi.getRemote(CashCostBillDataService.class);
	}
	
	@Override
	public String getNewId() {
		return cashCostBillDataService.getNewId();
	}

	@Override
	public boolean deleteBill(String id) {
		return cashCostBillDataService.deleteBill(id);
	}

	@Override
	public boolean saveBill(CashCostBillVO bill) {
		MyTableModel model = bill.getTableModel();
		ArrayList<CashCostItem>itemList = new ArrayList<CashCostItem>();
		for (int i = 0; i < model.getRowCount(); i++) {
			String[] s = model.getValueAtRow(i);
			CashCostItem item = new CashCostItem(s[3], Double.parseDouble(s[4]), s[5]);
			itemList.add(item);
		}
		return cashCostBillDataService.saveBill(new CashCostBillPO(bill.getDate(), bill.getTime(),
				bill.getId(), bill.getOperator(), bill.getState(), bill.getAccountId(), itemList));
	}

	@Override
	public CashCostBillVO getBill(String id) {
		CashCostBillPO bill = cashCostBillDataService.getBillById(id);
		return new CashCostBillVO(bill.getDate(), bill.getTime(), bill.getId(), bill.getOperator(), bill.getState(), bill.getAccountId());
	}
}
