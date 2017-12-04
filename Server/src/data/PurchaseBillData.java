package data;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.*;
import dataservice.PurchaseBillDataService;
import po.billpo.PurchaseBillItemsPO;
import po.billpo.PurchaseBillPO;
public class PurchaseBillData implements PurchaseBillDataService{

	@Override
	public boolean saveBill(PurchaseBillPO purchaseBill) throws RemoteException {
		
		ArrayList<PurchaseBillItemsPO> items=purchaseBill.getPurchaseBillItems();
		
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
					+items.get(i).getRemark()+"','"
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
		// TODO Auto-generated method stub
		try{
			Statement s = DataHelper.getInstance().createStatement();
			int r=s.executeUpdate("DELETE FROM PurchaseBill WHERE PBID="+id+";");
			if(r>0)return true;
		}catch(Exception e){
			  e.printStackTrace();
			   return false;
		}
		return false;
	}

	@Override
	public String getNewId() throws RemoteException {
		// TODO Auto-generated method stub
		String newId=null;
		Calendar now = Calendar.getInstance();
		int year=now.get(Calendar.YEAR), month=now.get(Calendar.MONTH),  day=now.get(Calendar.DAY_OF_MONTH);
		String date=year+"-"+month+"-"+day;
		int num=0;
		try{
			Statement s=DataHelper.getInstance().createStatement();
			ResultSet r=s.executeQuery("SELECT PBID FROM PurchaseBill WHERE PBTime>"
					+"'"+date+"' "+"AND PBTime<DATEADD(DAY,1,"+"'"+date+"');");
			while(r.next()){
				num++;
			}
			num++;
			
			newId="JHD-"+year+month+day+"-"+String.format("%5d", num);
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return newId;
	}

	@Override
	public PurchaseBillPO getBillById(String id) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<PurchaseBillItemsPO> items=new ArrayList<PurchaseBillItemsPO>();
		PurchaseBillPO purchaseBill=null;
		try{
			Statement s1=DataHelper.getInstance().createStatement();
			ResultSet r1=s1.executeQuery("SELECT * FROM PurchaseRecord WHERE PRID="+id+";");
			
			while(r1.next()){	
			    PurchaseBillItemsPO item=new PurchaseBillItemsPO(
					r1.getString("PRComID"),
					r1.getString("PRRemark"),
					r1.getInt("PRComQuantity"),
					r1.getDouble("PRComPrice"),
					r1.getDouble("PRComSum"));
			    items.add(item);
			}
			
			Statement s2=DataHelper.getInstance().createStatement();
			ResultSet r2=s2.executeQuery("SELECT * FROM PurchaseBill WHERE PBID="+id+";");
			while(r2.next()){
				String date=null, time=null;
				
				date=r2.getString("ILBTime").split(" ")[0];
				time=r2.getString("ILBTime").split(" ")[1];
				
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
