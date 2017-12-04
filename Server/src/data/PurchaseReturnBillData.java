package data;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import dataservice.PurchaseReturnBillDataService;
import po.billpo.PurchaseBillItemsPO;
import po.billpo.PurchaseBillPO;
import po.billpo.PurchaseReturnBillItemsPO;
import po.billpo.PurchaseReturnBillPO;;
public class PurchaseReturnBillData implements PurchaseReturnBillDataService{

	@Override
	public boolean saveBill(PurchaseReturnBillPO purchaseBill) throws RemoteException {
		// TODO Auto-generated method stub
        ArrayList<PurchaseReturnBillItemsPO> items=purchaseBill.getPurchaseReturnBillItems();
		
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
			int r=s.executeUpdate("DELETE FROM PurchaseRetrunBill WHERE PRBID="+id+";");
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
			ResultSet r=s.executeQuery("SELECT PRBID FROM PurchaseReturnBill WHERE PRBTime>"
					+"'"+date+"' "+"AND PRBTime<DATEADD(DAY,1,"+"'"+date+"');");
			while(r.next()){
				num++;
			}
			num++;
			
			newId="JHTHD-"+year+month+day+"-"+String.format("%5d", num);
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return newId;
	}

	@Override
	public PurchaseReturnBillPO getBillById(String id) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<PurchaseReturnBillItemsPO> items=new ArrayList<PurchaseReturnBillItemsPO>();
		PurchaseReturnBillPO purchaseReturnBill=null;
		try{
			Statement s1=DataHelper.getInstance().createStatement();
			ResultSet r1=s1.executeQuery("SELECT * FROM PurchaseReturnRecord WHERE PRRID="+id+";");
			
			while(r1.next()){	
			    PurchaseReturnBillItemsPO item=new PurchaseReturnBillItemsPO(
					r1.getString("PRRComID"),
					r1.getString("PRRRemark"),
					r1.getInt("PRRComQuantity"),
					r1.getDouble("PRRComPrice"),
					r1.getDouble("PRRComSum"));
			    items.add(item);
			}
			
			Statement s2=DataHelper.getInstance().createStatement();
			ResultSet r2=s2.executeQuery("SELECT * FROM PurchaseBill WHERE PBID="+id+";");
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
