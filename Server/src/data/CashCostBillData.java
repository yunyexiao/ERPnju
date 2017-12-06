package data;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import dataservice.CashCostBillDataService;
import po.billpo.CashCostItem;
import po.billpo.CashCostBillPO;
public class CashCostBillData extends UnicastRemoteObject implements CashCostBillDataService{
	
	protected CashCostBillData() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	private String tableName="CashCostBill";
	private String idName="CCBID";

	@Override
	public boolean saveBill(CashCostBillPO bill) throws RemoteException{
		// TODO Auto-generated method stub
		ArrayList<CashCostItem> items=new ArrayList<CashCostItem>();
		
		try{
			Statement s1 = DataHelper.getInstance().createStatement();
			int r1=s1.executeUpdate("INSERT INTO CashCostBill VALUES"
					+ "('"
					+bill.getId()+"','"
					+bill.getOperatorId()+"','"
					+bill.getAccountId()+"','"
					+bill.getSum()+"','"
					+bill.getDate()+" "+bill.getTime()+"','"
					+bill.getState()+"')");
			
			for(int i=0;i<items.size();i++){
			Statement s2 = DataHelper.getInstance().createStatement();
			int r2=s2.executeUpdate("INSERT INTO PurchaseRecord VALUES ('"
					+bill.getId()+"','"
					+items.get(i).getName()+"','"
					+items.get(i).getMoney()+"','"
					+items.get(i).getRemark()+"')");
			}
			if(r1>0)return true;
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
		newId="XJFYD-"+newId;
		
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
				String date=null, time=null;
				
				date=r2.getString("generateTime").split(" ")[0];
				time=r2.getString("generateTime").split(" ")[1];
				
				bill=new CashCostBillPO(
						date,
						time,
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
