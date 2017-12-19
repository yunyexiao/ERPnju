package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import blservice.billblservice.BillOperationService;
import blservice.billblservice.ChangeBillBLService;
import dataservice.ChangeBillDataService;
import ds_stub.ChangeBillDs_stub;
import po.billpo.BillPO;
import po.billpo.ChangeBillPO;
import po.billpo.ChangeItem;
import presentation.component.MyTableModel;
import presentation.tools.Timetools;
import rmi.Rmi;
import vo.billvo.BillVO;
import vo.billvo.ChangeBillVO;

public class ChangeBillBL implements ChangeBillBLService, BillOperationService{

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
	public boolean offsetBill(String id){
	    try{
            ChangeBillPO bill = changeBillDS.getBillById(id);
            ArrayList<ChangeItem> items = new ArrayList<>();
            bill.getCommodityList().forEach(i -> items.add(new ChangeItem(
                i.getCommodityId(), i.getChangedValue(), i.getOriginalValue())));
            ChangeBillPO offset = new ChangeBillPO(
                Timetools.getDate(), Timetools.getTime(), this.getNewId()
                , bill.getOperator(), BillPO.PASS, bill.getFlag(), items);
            return changeBillDS.saveBill(offset);
	    }catch(RemoteException e){
	        e.printStackTrace();
	        return false;
	    }
	}

	@Override
	public boolean copyBill(BillVO bill){
	    if(bill instanceof ChangeBillVO){
	        ChangeBillVO old = (ChangeBillVO) bill;
	        ChangeBillVO copy = new ChangeBillVO(
	            Timetools.getDate(), Timetools.getTime(), this.getNewId(), old.getOperator(),
	            BillVO.PASS, old.getFlag(), old.getTableModel()
	        );
	        return saveBill(copy);
	    }
	    return false;
	}

}
