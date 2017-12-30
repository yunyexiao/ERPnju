package data;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import dataservice.CashCostBillDataService;
import po.billpo.CashCostBillPO;
import po.billpo.CashCostItem;
public class CashCostBillData extends UnicastRemoteObject implements CashCostBillDataService{
	
	public CashCostBillData() throws RemoteException {
		super();
	}

	private String tableName="CashCostBill";
	private String idName="CCBID";

	@Override
	public boolean saveBill(CashCostBillPO bill) throws RemoteException{
		ArrayList<CashCostItem> items = bill.getCashcostList();
		
		try{
			boolean b1 = SQLQueryHelper.add(tableName, bill.getAllId()
					,bill.getOperator(),bill.getAccountId(),bill.getSum()
					,bill.getDate() + " "+bill.getTime(),bill.getState());
			boolean b2 = true;
			for(int i=0;i<items.size();i++) {
				b2 = b2 && SQLQueryHelper.add("CashCostRecord", bill.getAllId()
						,items.get(i).getName(),items.get(i).getMoney(),items.get(i).getRemark());
			}
			if(b1 && b2) return true;
		}catch(Exception e){
			  e.printStackTrace();
			  return false;
		}
		return false;
	}

	@Override
	public boolean deleteBill(String billid) throws RemoteException{
		return BillDataHelper.deleteBill(billid);
	}

	@Override
	public String getNewId() throws RemoteException {
		return BillDataHelper.getNewBillId(tableName,idName);
	}

	@Override
	public CashCostBillPO getBillById(String id) throws RemoteException{
		return BillDataHelper.getCashCostBill(id);
	}
}
