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

	private String tableName="CashCostBill",recordName="CashCostRecord";
	private String[] billAttributes={"CCBID","CCBOperatorID","CCBAccountID","CCBSum","generateTime","CCBCondition"};
	private String[] recordAttributes={"CCRID","CCRCostName","CCRMoney","CCRRemark"};

	@Override
	public boolean saveBill(CashCostBillPO bill) throws RemoteException{
		ArrayList<CashCostItem> items = bill.getCashcostList();
		boolean isExist=BillDataHelper.isBillExist(tableName, billAttributes[0], bill);
		Object[] billValues={bill.getAllId(),bill.getOperator(),bill.getAccountId(),bill.getSum(),bill.getSum(),
				bill.getDate()+" "+bill.getTime(),bill.getState()};

		try{
			if(!isExist){
			boolean b1 = SQLQueryHelper.add(tableName, bill.getAllId()
					,bill.getOperator(),bill.getAccountId(),bill.getSum()
					,bill.getDate() + " "+bill.getTime(),bill.getState());
			boolean b2 = true;
			for(int i=0;i<items.size();i++) {
				b2 = b2 && SQLQueryHelper.add(recordName, bill.getAllId()
						,items.get(i).getName(),items.get(i).getMoney(),items.get(i).getRemark());
			}
			return b1 && b2;
			}
			
			else{
				boolean b1=SQLQueryHelper.update(tableName, billAttributes, billValues);
				boolean b2=false;
				for(int i=0;i<items.size();i++){
					Object[] recordValues={bill.getAllId(),items.get(i).getName(),items.get(i).getMoney(),items.get(i).getRemark()};
					b2=b2||SQLQueryHelper.update(recordName, recordAttributes, recordValues);
				}
				return b1||b2;
			}
		}catch(Exception e){
			  e.printStackTrace();
			  return false;
		}
	}

	@Override
	public boolean deleteBill(String billid) throws RemoteException{
		return BillDataHelper.deleteBill(billid);
	}

	@Override
	public String getNewId() throws RemoteException {
		return BillDataHelper.getNewBillId(tableName,billAttributes[0]);
	}

	@Override
	public CashCostBillPO getBillById(String id) throws RemoteException{
		return BillDataHelper.getCashCostBill(id);
	}
}
