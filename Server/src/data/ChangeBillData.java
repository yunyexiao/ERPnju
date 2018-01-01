package data;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import dataservice.ChangeBillDataService;
import po.billpo.ChangeBillPO;
import po.billpo.ChangeItem;

public class ChangeBillData extends UnicastRemoteObject implements ChangeBillDataService{
	
    public ChangeBillData() throws RemoteException {
		super();
	}

	@Override
	public ChangeBillPO getBillById(String id, boolean isOver) throws RemoteException{
		return BillDataHelper.getChangeBill(id, isOver);
	}
	
	@Override
	public String getNewId(boolean isOver) throws RemoteException{
		String billName = isOver ? "InventoryOverflowBill" : "InventoryLostBill";
		String billId = isOver ? "IOBID" : "ILBID";
		return SQLQueryHelper.getNewId(billName, billId, "%05d");
	}

	@Override
	public boolean deleteBill(String id, boolean isOver) throws RemoteException {
		String billName = isOver ? "InventoryOverflowBill" : "InventoryLostBill";
		String billId = isOver ? "IOBID" : "ILBID";
		return SQLQueryHelper.getTrueDeleteResult(billName, billId, id);
	}

	@Override
	public boolean saveBill(ChangeBillPO bill) throws RemoteException {
		ArrayList<ChangeItem> items=bill.getCommodityList();
		String billName = bill.isOver() ? "InventoryOverflowBill" : "InventoryLostBill";
		String recordName=bill.isOver() ? "InventoryOverflowRecord" : "InventoryLostRecord";
		String[] billAttributes = bill.isOver() ? 
			new String[]{"IOBID","IOBOperatorID","IOBCondition"}:
			new String[]{"ILBID","ILBOperatorID","ILBCondition"};
		String[] recordAttributes = bill.isOver() ? 
			new String[]{"IORID","IORComQuantity","IOROverQuantity"}: 
			new String[]{"ILRID","ILRComQuantity","ILRLostQuantity"};
		try{
			boolean isExist=BillDataHelper.isBillExist(billName, billAttributes[0], bill);
			if(!isExist){
				boolean b1 = SQLQueryHelper.add(billName, bill.getAllId(), bill.getOperator()
						, bill.getState(), bill.getDate()+" "+bill.getTime());
				boolean b2 = true;
				for(int i=0;i<items.size();i++){
					b2 = b2 && SQLQueryHelper.add(recordName, bill.getAllId(),items.get(i).getCommodityId()
							,items.get(i).getOriginalValue(),items.get(i).getChangedValue());
				}
				return b1 && b2;
			}
			else{
				boolean b1=SQLQueryHelper.update(billName, billAttributes, new Object[]
						{bill.getAllId(), bill.getOperator(), bill.getState(), bill.getDate()+" "+bill.getTime()});
				boolean b2=false;
				for(int i=0;i<items.size();i++){
					ChangeItem item = items.get(i);
					b2=b2||SQLQueryHelper.update(recordName, recordAttributes, new Object[]
						{bill.getId(),item.getCommodityId(),item.getOriginalValue(),item.getChangedValue()});
				}
				return b1||b2;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
