package data;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import dataservice.PurchaseReturnBillDataService;
import po.billpo.PurchaseReturnBillPO;
import po.billpo.SalesItemsPO;;
public class PurchaseReturnBillData implements PurchaseReturnBillDataService{
	private String tableName="PurchaseReturnBill";
	private String idName="PRBID";

	@Override
	public boolean saveBill(PurchaseReturnBillPO purchaseBill) throws RemoteException {
		// TODO Auto-generated method stub
        ArrayList<SalesItemsPO> items=purchaseBill.getPurchaseReturnBillItems();
		
		try{
			Statement s1 = DataHelper.getInstance().createStatement();
			int r1=s1.executeUpdate("INSERT INTO PurchaseReturnBill VALUES"
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
			int r2=s2.executeUpdate("INSERT INTO PurchaseReturnRecord VALUES ('"
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

		String newId=SQLQueryHelper.getNewBillIdByDay(tableName);
		newId="JHTHD-"+newId;
		
		return newId;
	}

	@Override
	public PurchaseReturnBillPO getBillById(String id) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<SalesItemsPO> items=new ArrayList<SalesItemsPO>();
		PurchaseReturnBillPO purchaseReturnBill=null;
		try{
			//Statement s1=DataHelper.getInstance().createStatement();
			//ResultSet r1=s1.executeQuery("SELECT * FROM PurchaseReturnRecord WHERE PRRID="+id+";");
			ResultSet r1=SQLQueryHelper.getRecordByAttribute("PurchaseReturnRecord", "PRRID", id);
			while(r1.next()){	
			    SalesItemsPO item=new SalesItemsPO(
					r1.getString("PRRComID"),
					r1.getString("PRRRemark"),
					r1.getInt("PRRComQuantity"),
					r1.getDouble("PRRComPrice"),
					r1.getDouble("PRRComSum"));
			    items.add(item);
			}
			
			//Statement s2=DataHelper.getInstance().createStatement();
			//ResultSet r2=s2.executeQuery("SELECT * FROM PurchaseBill WHERE PBID="+id+";");
			ResultSet r2=SQLQueryHelper.getRecordByAttribute(tableName, idName, id);
			while(r2.next()){
				String date=null, time=null;
				
				date=r2.getString("ILBTime").split(" ")[0];
				time=r2.getString("ILBTime").split(" ")[1];
				
				purchaseReturnBill=new PurchaseReturnBillPO(
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
		return purchaseReturnBill;
	}

}
