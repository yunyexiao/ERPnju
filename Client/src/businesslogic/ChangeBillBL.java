package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import blservice.billblservice.ChangeBillBLService;
import dataservice.ChangeBillDataService;
import ds_stub.ChangeBillDs_stub;
import po.billpo.ChangeBillPO;
import po.billpo.ChangeItem;
import presentation.component.MyTableModel;
import rmi.Rmi;
import vo.billvo.ChangeBillVO;

public class ChangeBillBL implements ChangeBillBLService {

	private ChangeBillDataService changeBillDS;
	private boolean isOver = true;
	
	public ChangeBillBL(boolean isOver) {
		this.isOver = isOver;
		changeBillDS = Rmi.flag ? Rmi.getRemote(ChangeBillDataService.class) : new ChangeBillDs_stub();
	}
	
	@Override
	public String getNewId() {
		try {
			return changeBillDS.getNewId(isOver);
		} catch (RemoteException e) {
			e.printStackTrace();
			return "";
		}
	}

	@Override
	public boolean deleteBill(String id) {
		try {
			return changeBillDS.deleteBill(id);
		} catch (RemoteException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean saveBill(ChangeBillVO bill) {
		MyTableModel model = bill.getTableModel();
		ArrayList<ChangeItem> commodityList = new ArrayList<ChangeItem>();
		for (int i = 0; i < model.getRowCount(); i++) {
			String[] s = model.getValueAtRow(i);
			ChangeItem item = new ChangeItem(s[0], Integer.parseInt(s[2]), Integer.parseInt(s[3]));
			commodityList.add(item);
		}
		try {
			return changeBillDS.saveBill(new ChangeBillPO(bill.getDate(), bill.getTime(), bill.getId(), bill.getOperator(), bill.getState(), bill.getFlag(), commodityList));
		} catch (RemoteException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public ChangeBillVO getBill(String id) {
		ChangeBillPO bill;
		try {
			bill = changeBillDS.getBillById(id);
			return BillTools.toChangeBillVO(bill);
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}
}
