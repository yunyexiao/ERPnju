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
	/**
	 * 
	 */
	private static final long serialVersionUID = -952253035895433810L;
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
				
			    bill=new SalesBillPO(
			    		r2.getString("generateTime").split(" ")[0],
			    		r2.getString("generateTime").split(" ")[1],
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
		ResultSet res_Bill=null;
		ResultSet res_Record=null;
		try{
		if(isFuzzy){
			Statement s1 = DataHelper.getInstance().createStatement();
			res_Bill = s1.executeQuery("SELECT * FROM SalesBill WHERE "+field+"LIKE '%"+key+"%';");
		    
		    Statement s2 = DataHelper.getInstance().createStatement();
		    res_Record = s2.executeQuery("SELECT * FROM SalesRecord WHERE "+field+"LIKE '%"+key+"%';");
		}
		else if(!isFuzzy){
			res_Bill =SQLQueryHelper.getRecordByAttribute(billTableName, field, key);
			res_Record =SQLQueryHelper.getRecordByAttribute(recordTableName, field, key);
		}
		
		if(field.equals("SRComID")||field.equals("SRComQuantity")
				||field.equals("SRPrice")||field.equals("SRComSum")
				||field.equals("SRRemark")){
			ArrayList<String> ids=new ArrayList<String>();
			
			while(res_Record.next()){
				boolean isAdd=true;
				for(int i=0;i<ids.size();i++)
					if(res_Record.getString("SRID")==ids.get(i))isAdd=false;
				if(isAdd)ids.add(res_Record.getString("SRID"));
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
				
					bill=new SalesBillPO(
							s.getString("generateTime").split(" ")[0],
							s.getString("generateTime").split(" ")[1],
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
				   bills.add(bill);	
				}			   
	
			}
		}
		else{
			
			while(res_Bill.next()){
				ArrayList<SalesItemsPO> items=new ArrayList<SalesItemsPO>();
				ResultSet r =SQLQueryHelper.getRecordByAttribute(recordTableName, recordIdName, res_Bill.getString("SBID"));
				while(r.next()){
					SalesItemsPO item=new SalesItemsPO(
							r.getString("SRComID"),
							r.getString("SRRemark"),
							r.getInt("SRComQuantity"),
							r.getDouble("SRPrice"),
							r.getDouble("SRComSum"));
					    items.add(item);
				}
				
				
				SalesBillPO bill=new SalesBillPO(
						res_Bill.getString("generateTime").split(" ")[0],
						res_Bill.getString("generateTime").split(" ")[1],
						res_Bill.getString("SBID"),
						res_Bill.getString("SBOperatorID"),
						res_Bill.getInt("PBCondition"),
						res_Bill.getString("SBCustomerID"),
						res_Bill.getString("SBSalesmanName"),
						res_Bill.getString("SBRemark"),
						res_Bill.getString("SBPromotionID"),
						res_Bill.getDouble("SBBeforeDiscount"),
						res_Bill.getDouble("SBDiscount"),
						res_Bill.getDouble("SBCoupon"),
						res_Bill.getDouble("SBAfterDiscount"),
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
	public ArrayList<SalesBillPO> getBillByDate(String from, String to) throws RemoteException {

		ArrayList<SalesBillPO> bills=new ArrayList<SalesBillPO>();
		try{
			
			Statement s=DataHelper.getInstance().createStatement();
			ResultSet r=s.executeQuery("SELECT * FROM "+billTableName+
					" WHERE generateTime>'"+from+"' AND generateTime<DATEADD(DAY,1,"+"'"+to+"');");
			while(r.next()){
				SalesBillPO bill=new SalesBillPO();
				bill.setId(r.getString("SBID"));
				bill.setCustomerId(r.getString("SBCustomerID"));
				bill.setOperatorId(r.getString("SBOperatorID"));
				bill.setAfterDiscount(r.getDouble("SBBeforeDiscount"));
				bill.setBeforeDiscount(r.getDouble("SBBeforeDiscount"));
				bill.setCoupon(r.getDouble("SBCoupon"));
				bill.setDate(r.getString("generateTime").split(" ")[0]);
				bill.setTime(r.getString("generateTime").split(" ")[1]);
				bill.setDiscount(r.getDouble("SBDiscount"));
				bill.setPromotionId(r.getString("SBPromotionID"));
				bill.setRemark(r.getString("SBRemark"));
				bill.setSalesManName(r.getString("SBSalesmanName"));
				bill.setState(r.getInt("PBCondition"));
				
				bills.add(bill);
			}
			
			for(int i=0;i<bills.size();i++){
				Statement s1=DataHelper.getInstance().createStatement();
				ResultSet r1=s1.executeQuery("SELECT * FROM "+recordTableName+
						" WHERE SRID="+bills.get(i).getId()+";");
				ArrayList<SalesItemsPO> items=new ArrayList<SalesItemsPO>();
				
				while(r1.next()){
					SalesItemsPO item=new SalesItemsPO(
							r1.getString("SRComID"),
							r1.getString("SRRemark"),
							r1.getInt("SRComQuantity"),
							r1.getDouble("SRPrice"),
							r1.getDouble("SRComSum"));
					items.add(item);
				}
				
				bills.get(i).setSalesBillItems(items);

			}
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return bills;
	}
	
}
