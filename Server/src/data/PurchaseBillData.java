package data;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import dataservice.PurchaseBillDataService;
import po.billpo.PurchaseBillPO;
import po.billpo.SalesItemsPO;
public class PurchaseBillData extends UnicastRemoteObject implements PurchaseBillDataService{
	public PurchaseBillData() throws RemoteException {
		super();
	}

	private String tableName="PurchaseBill";
	private String idName="PBID";

	@Override
	public boolean saveBill(PurchaseBillPO bill) throws RemoteException {
		
		ArrayList<SalesItemsPO> items = bill.getPurchaseBillItems();
		try{
			boolean b1 = SQLQueryHelper.add(tableName, bill.getAllId()
					,bill.getSupplierId(),bill.getOperator(),bill.getSum(),bill.getRemark()
					,bill.getState(),bill.getDate() + " "+bill.getTime());
			boolean b2 = true;
			for(int i=0;i<items.size();i++){
				b2 = b2 && SQLQueryHelper.add("PurchaseRecord", bill.getAllId()
						,items.get(i).getComId(),items.get(i).getComQuantity(),items.get(i).getComSum()
						,items.get(i).getComRemark(),items.get(i).getComPrice());
			}
			return b1 && b2;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteBill(String id) throws RemoteException {
		return BillDataHelper.deleteBill(id);
	}

	@Override
	public String getNewId() throws RemoteException {
		return BillDataHelper.getNewBillId(tableName, idName);
	}

	@Override
	public PurchaseBillPO getBillById(String id) throws RemoteException {
		return BillDataHelper.getPurchaseBill(id);
	}

	@Override
	public ArrayList<PurchaseBillPO> getBillsBy(String field, String key, boolean isFuzzy) throws RemoteException {

		ArrayList<PurchaseBillPO> bills=new ArrayList<PurchaseBillPO>();
		ResultSet res_Bill=null;
		ResultSet res_Record=null;
		try{
			if(isFuzzy){
				Statement s1 = DataHelper.getInstance().createStatement();
			    res_Bill = s1.executeQuery("SELECT * FROM PurchaseBill WHERE "+field+" LIKE '%"+key+"%';");
			    
			    Statement s2 = DataHelper.getInstance().createStatement();
			    res_Record = s2.executeQuery("SELECT * FROM PurchaseRecord WHERE "+field+" LIKE '%"+key+"%';");
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
				for(int i = 0; i < ids.size(); i++) bills.add(BillDataHelper.getPurchaseBill(ids.get(i)));
			}
			else{
				while(res_Bill.next()) bills.add(BillDataHelper.getPurchaseBill(res_Bill.getString("PBID")));
			}

		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return bills;
	}

	@Override
	public ArrayList<PurchaseBillPO> getBillByDate(String from, String to) throws RemoteException {
		ArrayList<PurchaseBillPO> bills=new ArrayList<PurchaseBillPO>();
		try{
			Statement s=DataHelper.getInstance().createStatement();
			ResultSet r=s.executeQuery("SELECT * FROM "+tableName+
					" WHERE generateTime>'"+from+"' AND generateTime<DATEADD(DAY,1,"+"'"+to+"');");
			while(r.next()) bills.add(BillDataHelper.getPurchaseBill(r.getString("PBID")));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return bills;
	}
}
