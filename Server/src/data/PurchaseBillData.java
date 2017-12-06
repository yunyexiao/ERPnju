package data;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.*;
import dataservice.PurchaseBillDataService;
import po.billpo.PurchaseBillPO;
import po.billpo.SalesItemsPO;
public class PurchaseBillData extends UnicastRemoteObject implements PurchaseBillDataService{
	protected PurchaseBillData() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	private String tableName="PurchaseBill";
	private String idName="PBID";

	@Override
	public boolean saveBill(PurchaseBillPO purchaseBill) throws RemoteException {
		
		ArrayList<SalesItemsPO> items=purchaseBill.getPurchaseBillItems();
		
		try{
			Statement s1 = DataHelper.getInstance().createStatement();
			int r1=s1.executeUpdate("INSERT INTO PurchaseBill VALUES"
					+ "('"
					+purchaseBill.getId()+"','"
					+purchaseBill.getSupplierId()+"','"
					+purchaseBill.getOperatorId()+"','"
					+purchaseBill.getSum()+"','"
					+purchaseBill.getRemark()+"','"
					+purchaseBill.getState()+"','"
					+purchaseBill.getDate()+" "+purchaseBill.getTime()+"')");
			
			for(int i=0;i<items.size();i++){
			Statement s2 = DataHelper.getInstance().createStatement();
			int r2=s2.executeUpdate("INSERT INTO PurchaseRecord VALUES ('"
					+purchaseBill.getId()+"','"
					+items.get(i).getComId()+"','"
					+items.get(i).getComQuantity()+"','"
					+items.get(i).getComSum()+"','"
					+items.get(i).getComRemark()+"','"
					+items.get(i).getComPrice()+"')");
			}
			if(r1>0)return true;
		}catch(Exception e){
			  e.printStackTrace();
			   return false;
		}
		return false;
	}

	@Override
	public boolean deleteBill(String id) throws RemoteException {
	
		boolean res=SQLQueryHelper.getTrueDeleteResult(tableName, idName, id);
		
		return res;
	}

	@Override
	public String getNewId() throws RemoteException {

		String newId=SQLQueryHelper.getNewBillIdByDay(tableName,idName);
		newId="JHD-"+newId;
		
		return newId;
	}

	@Override
	public PurchaseBillPO getBillById(String id) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<SalesItemsPO> items=new ArrayList<SalesItemsPO>();
		PurchaseBillPO purchaseBill=null;
		try{
			//Statement s1=DataHelper.getInstance().createStatement();
			//ResultSet r1=s1.executeQuery("SELECT * FROM PurchaseRecord WHERE PRID="+id+";");
			ResultSet r1=SQLQueryHelper.getRecordByAttribute("PurchaseRecord", "PRID", id);
	
			while(r1.next()){	
			    SalesItemsPO item=new SalesItemsPO(
					r1.getString("PRComID"),
					r1.getString("PRRemark"),
					r1.getInt("PRComQuantity"),
					r1.getDouble("PRComPrice"),
					r1.getDouble("PRComSum"));
			    items.add(item);
			}
			
			//Statement s2=DataHelper.getInstance().createStatement();
			//ResultSet r2=s2.executeQuery("SELECT * FROM PurchaseBill WHERE PBID="+id+";");
			ResultSet r2=SQLQueryHelper.getRecordByAttribute(tableName, idName, id);
			while(r2.next()){
				String date=null, time=null;
				
				date=r2.getString("generateTime").split(" ")[0];
				time=r2.getString("generateTime").split(" ")[1];
				
				purchaseBill=new PurchaseBillPO(
						date,
						time,
						r2.getString("PBID"),
						r2.getString("PBOperatorID"),
						r2.getInt("PBCondition"),
						r2.getString("PBSupplierID"),
						r2.getString("PBRemark"),
						r2.getDouble("PBSum"),
						items);
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return purchaseBill;
	}

}
