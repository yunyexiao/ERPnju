package data;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import dataservice.PurchaseReturnBillDataService;
import po.billpo.PurchaseReturnBillPO;
import po.billpo.SalesItemsPO;;
public class PurchaseReturnBillData extends UnicastRemoteObject implements PurchaseReturnBillDataService{
	
	protected PurchaseReturnBillData() throws RemoteException {
		super();
		
	}

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

		String newId=SQLQueryHelper.getNewBillIdByDay(tableName,idName);
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
				
				purchaseReturnBill=new PurchaseReturnBillPO(
						r2.getString("generateTime").split(" ")[0],
						r2.getString("generateTime").split(" ")[1],
						r2.getString("PRBID"),
						r2.getString("PRBOperatorID"),
						r2.getInt("PRBCondition"),
						r2.getString("PRBSupplierID"),
						r2.getString("PRBRemark"),
						r2.getDouble("PRBSum"),
						items);
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return purchaseReturnBill;
	}

	@Override
	public ArrayList<PurchaseReturnBillPO> getBillsByDate(String from, String to) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<PurchaseReturnBillPO> bills=new ArrayList<PurchaseReturnBillPO>();
		try{
			Statement s=DataHelper.getInstance().createStatement();
			ResultSet r=s.executeQuery("SELECT * FROM "+tableName+
					" WHERE generateTime>'"+from+"' AND generateTime<DATEADD(DAY,1,"+"'"+to+"');");
			
			while(r.next()){
				PurchaseReturnBillPO bill=new PurchaseReturnBillPO(
						r.getString("generateTime").split(" ")[0],
						r.getString("generateTime").split(" ")[1],
						r.getString("PRBID"),
						r.getString("PRBOperatorID"),
						r.getInt("PRBCondition"),
						r.getString("PRBSupplierID"),
						r.getString("PRBRemark"),
						r.getDouble("PRBSum"),
						null);
			}
			
			for(int i=0;i<bills.size();i++){
				Statement s1=DataHelper.getInstance().createStatement();
				ResultSet r1=s1.executeQuery("SELECT * FROM PurchaseReturnRecord WHERE PRRID="+bills.get(i).getId()+";");
				ArrayList<SalesItemsPO> items=new ArrayList<SalesItemsPO>();
				
				while(r1.next()){
					SalesItemsPO item=new SalesItemsPO(
							r1.getString("PRRComID"),
							r1.getString("PRRRemark"),
							r1.getInt("PRRComQuantity"),
							r1.getDouble("PRRComPrice"),
							r1.getDouble("PRRComSum"));
					items.add(item);
				}
				
				bills.get(i).setPurchaseReturnBillItems(items);

			}


		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return bills;
	}

}
