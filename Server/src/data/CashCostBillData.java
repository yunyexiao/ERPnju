package data;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
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
		ArrayList<CashCostItem> items = new ArrayList<CashCostItem>();
		
		try{
			boolean b1 = SQLQueryHelper.add(tableName, bill.getId()
					,bill.getOperator(),bill.getAccountId(),bill.getSum()
					,bill.getDate() + " "+bill.getTime(),bill.getState());
			boolean b2 = true;
			for(int i=0;i<items.size();i++) {
				b2 = b2 && SQLQueryHelper.add(tableName, bill.getAllId()
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
		// TODO Auto-generated method stub
        boolean res=SQLQueryHelper.getTrueDeleteResult(tableName, idName, billid);
		
		return res;
	}

	@Override
	public String getNewId() throws RemoteException {
		// TODO Auto-generated method stub
		String newId=SQLQueryHelper.getNewBillIdByDay(tableName,idName);
		return newId;
		
	}

	@Override
	public CashCostBillPO getBillById(String id) throws RemoteException{
		// TODO Auto-generated method stub
		ArrayList<CashCostItem> items=new ArrayList<CashCostItem>();
		CashCostBillPO bill=null;
		try{
			//Statement s1=DataHelper.getInstance().createStatement();
			//ResultSet r1=s1.executeQuery("SELECT * FROM PurchaseRecord WHERE PRID="+id+";");
			ResultSet r1=SQLQueryHelper.getRecordByAttribute("CashCostRecord", "CCRID", id);
	
			while(r1.next()){	
				CashCostItem item=new CashCostItem(
					r1.getString("CCRCostName"),
					r1.getDouble("CCRMoney"),
					r1.getString("CCRRemark"));
			    items.add(item);
			}
			
			//Statement s2=DataHelper.getInstance().createStatement();
			//ResultSet r2=s2.executeQuery("SELECT * FROM PurchaseBill WHERE PBID="+id+";");
			ResultSet r2=SQLQueryHelper.getRecordByAttribute(tableName, idName, id);
			while(r2.next()){
				
				bill=new CashCostBillPO(
						r2.getString("generateTime").split(" ")[0],
						r2.getString("generateTime").split(" ")[1],
						r2.getString("CCBID"),
						r2.getString("CCBOperatorID"),
						r2.getInt("CCBCondition"),
						r2.getString("CCBAccountID"),
						items,
						r2.getDouble("CCBSum"));
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return bill;
	}

}
