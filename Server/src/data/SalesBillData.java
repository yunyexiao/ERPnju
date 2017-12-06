package data;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import dataservice.SalesBillDataService;
import po.billpo.SalesItemsPO;
import po.billpo.SalesBillPO;
public class SalesBillData extends UnicastRemoteObject implements SalesBillDataService{
	private String billTableName="SalesBill";
	private String recordTableName="SalesRecord";
	private String billIdName="SBID";
	private String recordIdName="SRID";

	protected SalesBillData() throws RemoteException {
		super();
		
	}

	@Override
	public boolean saveBill(SalesBillPO bill) throws RemoteException {
		
		ArrayList<SalesItemsPO> items=bill.getSalesBillItems();
		try{
			Statement s1 = DataHelper.getInstance().createStatement();
			int r1=s1.executeUpdate("INSERT INTO SalesBill VALUES"
					+ "('"
					+bill.getId()+"','"
					+bill.getCustomerId()+"','"
					+bill.getSalesManName()+"','"
					+bill.getOperatorId()+"','"
					+bill.getBeforeDiscount()+"','"
					+bill.getDiscount()+"','"
					+bill.getCoupon()+"',' "
					+bill.getAfterDiscount()+"','"
					+bill.getRemark()+"','"
					+bill.getPromotionId()+"','"
					+bill.getDate()+" "+bill.getTime()+"','"
					+bill.getState()+"')");
			
			for(int i=0;i<items.size();i++){
			Statement s2 = DataHelper.getInstance().createStatement();
			int r2=s2.executeUpdate("INSERT INTO SalesRecord VALUES ('"
					+bill.getId()+"','"
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
	public boolean deleteBill(String billid) throws RemoteException {

		boolean res=SQLQueryHelper.getTrueDeleteResult(billTableName, billIdName, billid);
		
		return res;
	}

	@Override
	public String getNewId() throws RemoteException {
		
		String newId=SQLQueryHelper.getNewBillIdByDay(billTableName,billIdName);
		newId="XSD-"+newId;
		
		return newId;		
	}

	@Override
	public SalesBillPO getBillById(String id) throws RemoteException {
		ArrayList<SalesItemsPO> items=new ArrayList<SalesItemsPO>();
		SalesBillPO bill=null;
		try{
			
			ResultSet r1=SQLQueryHelper.getRecordByAttribute(recordTableName, recordIdName, id);
	
			while(r1.next()){	
			    SalesItemsPO item=new SalesItemsPO(
					r1.getString("SRComID"),
					r1.getString("SRRemark"),
					r1.getInt("SRComQuantity"),
					r1.getDouble("SRPrice"),
					r1.getDouble("SRComSum"));
			    items.add(item);
			}
			
			ResultSet r2=SQLQueryHelper.getRecordByAttribute(billTableName, billIdName, id);
			while(r2.next()){
				String date=null, time=null;
				
				date=r2.getString("generateTime").split(" ")[0];
				time=r2.getString("generateTime").split(" ")[1];
				
			    bill=new SalesBillPO(
						date,
						time,
						r2.getString("SBID"),
						r2.getString("SBOperatorID"),
						r2.getInt("SBCondition"),
						r2.getString("SBCustomerID"),
						r2.getString("SBSalesmanName"),
						r2.getString("SBRemark"),
						r2.getString("SBPromotionID"),
						r2.getDouble("SBBeforeDiscount"),
						r2.getDouble("SBDiscount"),
						r2.getDouble("SBCoupon"),
						r2.getDouble("SBAfterDiscount"),
						items);
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return bill;
	}
	

	@Override
	public ArrayList<SalesBillPO> getBillsBy(String field, String key, boolean isFuzzy) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<SalesBillPO> bills=new ArrayList<SalesBillPO>();
		ResultSet res1=null;
		ResultSet res2=null;
		try{
		if(isFuzzy){
			Statement s1 = DataHelper.getInstance().createStatement();
		    res1 = s1.executeQuery("SELECT * FROM SalesBill WHERE "+field+"LIKE '%"+key+"%';");
		    
		    Statement s2 = DataHelper.getInstance().createStatement();
		    res2 = s2.executeQuery("SELECT * FROM SalesRecord WHERE "+field+"LIKE '%"+key+"%';");
		}
		else if(!isFuzzy){
			res1 =SQLQueryHelper.getRecordByAttribute(billTableName, field, key);
			res2 =SQLQueryHelper.getRecordByAttribute(recordTableName, field, key);
		}
		
		if(field.equals("SRComID")||field.equals("SRComQuantity")
				||field.equals("SRPrice")||field.equals("SRComSum")
				||field.equals("SRRemark")){
			ArrayList<String> ids=new ArrayList<String>();
			
			while(res2.next()){
				boolean isAdd=true;
				for(int i=0;i<ids.size();i++)
					if(res2.getString("SRID")==ids.get(i))isAdd=false;
				if(isAdd)ids.add(res2.getString("SRID"));
			}
			
			for(int i=0;i<ids.size();i++){
				ArrayList<SalesItemsPO> items=new ArrayList<SalesItemsPO>();
				SalesBillPO bill=null;
				
				ResultSet r =SQLQueryHelper.getRecordByAttribute(recordTableName, recordIdName, ids.get(i));
				while(r.next()){
					 SalesItemsPO item=new SalesItemsPO(
								r.getString("SRComID"),
								r.getString("SRRemark"),
								r.getInt("SRComQuantity"),
								r.getDouble("SRPrice"),
								r.getDouble("SRComSum"));
						    items.add(item);
				}
				
			   ResultSet s =SQLQueryHelper.getRecordByAttribute(billTableName, billIdName, ids.get(i));
			   
			   while(s.next()){
					String date=null, time=null;
					
					date=s.getString("generateTime").split(" ")[0];
					time=s.getString("generateTime").split(" ")[1];
					
					bill=new SalesBillPO(
							date,
							time,
							s.getString("SBID"),
							s.getString("SBOperatorID"),
							s.getInt("PBCondition"),
							s.getString("SBCustomerID"),
							s.getString("SBSalesmanName"),
							s.getString("SBRemark"),
							s.getString("SBPromotionID"),
							s.getDouble("SBBeforeDiscount"),
							s.getDouble("SBDiscount"),
							s.getDouble("SBCoupon"),
							s.getDouble("SBAfterDiscount"),
							items);
				}			   
			   bills.add(bill);	
			}
		}
		else{
			
			while(res1.next()){
				ArrayList<SalesItemsPO> items=new ArrayList<SalesItemsPO>();
				ResultSet r =SQLQueryHelper.getRecordByAttribute(recordTableName, recordIdName, res1.getString("SBID"));
				while(r.next()){
					SalesItemsPO item=new SalesItemsPO(
							r.getString("SRComID"),
							r.getString("SRRemark"),
							r.getInt("SRComQuantity"),
							r.getDouble("SRPrice"),
							r.getDouble("SRComSum"));
					    items.add(item);
				}
				
				String date=null, time=null;
				
				date=res1.getString("generateTime").split(" ")[0];
				time=res1.getString("generateTime").split(" ")[1];
				
				SalesBillPO bill=new SalesBillPO(
						date,
						time,
						res1.getString("SBID"),
						res1.getString("SBOperatorID"),
						res1.getInt("PBCondition"),
						res1.getString("SBCustomerID"),
						res1.getString("SBSalesmanName"),
						res1.getString("SBRemark"),
						res1.getString("SBPromotionID"),
						res1.getDouble("SBBeforeDiscount"),
						res1.getDouble("SBDiscount"),
						res1.getDouble("SBCoupon"),
						res1.getDouble("SBAfterDiscount"),
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

}
