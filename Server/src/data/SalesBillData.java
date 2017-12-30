package data;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import dataservice.SalesBillDataService;
import po.billpo.SalesBillPO;
import po.billpo.SalesItemsPO;
public class SalesBillData extends UnicastRemoteObject implements SalesBillDataService{
	private static final long serialVersionUID = -952253035895433810L;
	private String billTableName="SalesBill";
	private String recordTableName="SalesRecord";
	private String billIdName="SBID";
	private String recordIdName="SRID";

	public SalesBillData() throws RemoteException {
		super();
		
	}

	@Override
	public boolean saveBill(SalesBillPO bill) throws RemoteException {
		ArrayList<SalesItemsPO> items = bill.getSalesBillItems();
		try{
			boolean b1 = SQLQueryHelper.add(billTableName, bill.getAllId()
					,bill.getCustomerId(),bill.getSalesManName(),bill.getOperator(),bill.getBeforeDiscount()
					,bill.getDiscount(),bill.getCoupon(),bill.getAfterDiscount(),bill.getRemark()
					,bill.getPromotionId(),bill.getDate() + " "+bill.getTime(),bill.getState());
			boolean b2 = true;
			for(int i=0;i<items.size();i++){
				b2 = b2 && SQLQueryHelper.add(recordTableName, bill.getAllId()
						,items.get(i).getComId(),items.get(i).getComQuantity(),items.get(i).getComSum()
						,items.get(i).getComRemark(),items.get(i).getComPrice());
			}
			return b1&&b2;
		}catch(Exception e){
			  e.printStackTrace();
			   return false;
		}
	}

	@Override
	public boolean deleteBill(String billid) throws RemoteException {
		return BillDataHelper.deleteBill(billid);
	}

	@Override
	public String getNewId() throws RemoteException {
		return BillDataHelper.getNewBillId(billTableName,billIdName);		
	}

	@Override
	public SalesBillPO getBillById(String id) throws RemoteException {
		return BillDataHelper.getSalesBill(id);
	}
	

	@Override
	public ArrayList<SalesBillPO> getBillsBy(String field, String key, boolean isFuzzy) throws RemoteException {
		ArrayList<SalesBillPO> bills=new ArrayList<SalesBillPO>();
		ResultSet res_Bill=null;
		ResultSet res_Record=null;
		try{
			if(isFuzzy){
				Statement s1 = DataHelper.getInstance().createStatement();
				res_Bill = s1.executeQuery("SELECT * FROM SalesBill WHERE "+field+" LIKE '%"+key+"%';");
			    
			    Statement s2 = DataHelper.getInstance().createStatement();
			    res_Record = s2.executeQuery("SELECT * FROM SalesRecord WHERE "+field+" LIKE '%"+key+"%';");
			}
			else {
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
				for(int i = 0; i < ids.size(); i++) bills.add(BillDataHelper.getSalesBill(ids.get(i)));
			}
			else while(res_Bill.next()) bills.add(BillDataHelper.getSalesBill(res_Bill.getString("SBID")));	
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
			while(r.next()) bills.add(BillDataHelper.getSalesBill(r.getString("SBID")));
			return bills;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
}
