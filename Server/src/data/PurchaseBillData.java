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
	public PurchaseBillData() throws RemoteException {
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
					+purchaseBill.getOperator()+"','"
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
				
				purchaseBill=new PurchaseBillPO(
						r2.getString("generateTime").split(" ")[0],
						r2.getString("generateTime").split(" ")[1],
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

	@Override
	public ArrayList<PurchaseBillPO> getBillsBy(String field, String key, boolean isFuzzy) throws RemoteException {

		ArrayList<PurchaseBillPO> bills=new ArrayList<PurchaseBillPO>();
		ResultSet res_Bill=null;
		ResultSet res_Record=null;
		try{
			if(isFuzzy){
				Statement s1 = DataHelper.getInstance().createStatement();
			    res_Bill = s1.executeQuery("SELECT * FROM PurchaseBill WHERE "+field+"LIKE '%"+key+"%';");
			    
			    Statement s2 = DataHelper.getInstance().createStatement();
			    res_Record = s2.executeQuery("SELECT * FROM PurchaseRecord WHERE "+field+"LIKE '%"+key+"%';");
			}
			else if(!isFuzzy){
				res_Bill =SQLQueryHelper.getRecordByAttribute(tableName, field, key);
				res_Record=SQLQueryHelper.getRecordByAttribute("PurchaseRecord", field, key);
			}
			
			if(field.equals("PRComID")||field.equals("PRComQuantity")
					||field.equals("PRComPrice")||field.equals("PRComSum")
					||field.equals("PRRemark")){
				ArrayList<String> ids=new ArrayList<String>();				
				
				while(res_Record.next()){
					boolean isAdd=true;
					for(int i=0;i<ids.size();i++)
						if(res_Record.getString("PRID")==ids.get(i))isAdd=false;
					if(isAdd)ids.add(res_Record.getString("PRID"));
				}
				
				for(int i=0;i<ids.size();i++){
					ArrayList<SalesItemsPO> items=new ArrayList<SalesItemsPO>();
					PurchaseBillPO bill=null;
					ResultSet r =SQLQueryHelper.getRecordByAttribute("PurchaseRecord", "PRID", ids.get(i));
					while(r.next()){
						 SalesItemsPO item=new SalesItemsPO(
									r.getString("PRComID"),
									r.getString("PRRemark"),
									r.getInt("PRComQuantity"),
									r.getDouble("PRComPrice"),
									r.getDouble("PRComSum"));
							    items.add(item);
					}
					
					ResultSet s =SQLQueryHelper.getRecordByAttribute(tableName,idName, ids.get(i));
					while(s.next()){
						
						bill=new PurchaseBillPO(
								s.getString("generateTime").split(" ")[0],
								s.getString("generateTime").split(" ")[1],
								s.getString("PBID"),
								s.getString("PBOperatorID"),
								s.getInt("PBCondition"),
								s.getString("PBSupplierID"),
								s.getString("PBRemark"),
								s.getDouble("PBSum"),
								items);
						bills.add(bill);
					}
				}				
			}
			else{
				while(res_Bill.next()){
					ArrayList<SalesItemsPO> items=new ArrayList<SalesItemsPO>();
					ResultSet r =SQLQueryHelper.getRecordByAttribute("PurchaseRecord", "PRID", res_Bill.getString("PBID"));
					while(r.next()){
						 SalesItemsPO item=new SalesItemsPO(
									r.getString("PRComID"),
									r.getString("PRRemark"),
									r.getInt("PRComQuantity"),
									r.getDouble("PRComPrice"),
									r.getDouble("PRComSum"));
							    items.add(item);
					}
					
					PurchaseBillPO bill=new PurchaseBillPO(
							res_Bill.getString("generateTime").split(" ")[0],
							res_Bill.getString("generateTime").split(" ")[1],
							res_Bill.getString("PBID"),
							res_Bill.getString("PBOperatorID"),
							res_Bill.getInt("PBCondition"),
							res_Bill.getString("PBSupplierID"),
							res_Bill.getString("PBRemark"),
							res_Bill.getDouble("PBSum"),
							items);
							
				    bills.add(bill);	
				}
			}

		}catch(Exception e){
			e.printStackTrace();
			return null;
		}

		return bills;
	}

	@Override
	public ArrayList<PurchaseBillPO> getBillByDate(String from, String to) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<PurchaseBillPO> bills=new ArrayList<PurchaseBillPO>();
		try{
			Statement s=DataHelper.getInstance().createStatement();
			ResultSet r=s.executeQuery("SELECT * FROM "+tableName+
					" WHERE generateTime>'"+from+"' AND generateTime<DATEADD(DAY,1,"+"'"+to+"');");
			
			while(r.next()){
				PurchaseBillPO bill=new PurchaseBillPO();
				bill.setDate(r.getString("generateTime").split(" ")[0]);
				bill.setId(r.getString("PBID"));
				bill.setOperatorId(r.getString("PBOperatorID"));
				bill.setRemark(r.getString("PBRemark"));
				bill.setState(r.getInt("PBCondition"));
				bill.setSum(r.getDouble("PBSum"));
				bill.setSupplierId(r.getString("PBSupplierID"));
				bill.setTime(r.getString("generateTime").split(" ")[1]);
				bills.add(bill);
				}
			
			for(int i=0;i<bills.size();i++){
				Statement s1=DataHelper.getInstance().createStatement();
				ResultSet r1=s1.executeQuery("SELECT * FROM PurchaseRecord WHERE PRID="+bills.get(i).getId()+";");
				ArrayList<SalesItemsPO> items=new ArrayList<SalesItemsPO>();
				
				while(r1.next()){
					SalesItemsPO item=new SalesItemsPO(
							r1.getString("PRComID"),
							r1.getString("PRRemark"),
							r1.getInt("PRComQuantity"),
							r1.getDouble("PRComPrice"),
							r1.getDouble("PRComSum"));
					items.add(item);
				}
				
				bills.get(i).setPurchaseBillItems(items);;

			}
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return bills;
	}

}
