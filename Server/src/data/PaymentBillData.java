package data;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.util.ArrayList;

import dataservice.PaymentBillDataService;
import po.billpo.PaymentBillPO;
import po.billpo.TransferItem;
public class PaymentBillData extends UnicastRemoteObject implements PaymentBillDataService{
	
	private String billTableName="PaymentBill";
	private String recordTableName="PaymentRecord";
	private String billIdName="PBID";
	private String recordIdName="PRID";

	public PaymentBillData() throws RemoteException {
		super();
	}

	@Override
	public boolean saveBill(PaymentBillPO bill) throws RemoteException {

		ArrayList<TransferItem> items=bill.getTransferList();
		try{
			boolean b1 = SQLQueryHelper.add(billTableName, bill.getId()
					,bill.getCustomerId(),bill.getOperator(),bill.getSum()
					,bill.getDate() + " "+bill.getTime(),bill.getState());
			boolean b2 = true;
			for(int i=0;i<items.size();i++){
				b2 = b2 && SQLQueryHelper.add(recordTableName, bill.getAllId()
						,items.get(i).getAccountId(),items.get(i).getMoney(),items.get(i).getRemark());
			}
			if(b1 && b2) return true;
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
	public PaymentBillPO getBillById(String id) throws RemoteException {
		ArrayList<TransferItem> items=new ArrayList<TransferItem>();
		PaymentBillPO bill=null;
		try{
			//Statement s1=DataHelper.getInstance().createStatement();
			//ResultSet r1=s1.executeQuery("SELECT * FROM PurchaseRecord WHERE PRID="+id+";");
			ResultSet r1=SQLQueryHelper.getRecordByAttribute(recordTableName, recordIdName, id);
	
			while(r1.next()){	
				TransferItem item=new TransferItem(
					r1.getString("PRAccountID"),
					r1.getDouble("PRTransfer"),
					r1.getString("PRRemark"));
			    items.add(item);
			}
			
			//Statement s2=DataHelper.getInstance().createStatement();
			//ResultSet r2=s2.executeQuery("SELECT * FROM PurchaseBill WHERE PBID="+id+";");
			ResultSet r2=SQLQueryHelper.getRecordByAttribute(billTableName, billIdName, id);
			while(r2.next()){
				String date=null, time=null;
				
				date=r2.getString("generateTime").split(" ")[0];
				time=r2.getString("generateTime").split(" ")[1];
				
				bill=new PaymentBillPO(
						date,
						time,
						r2.getString("PBID"),
						r2.getString("PBOperatorID"),
						r2.getInt("PBCondition"),
						r2.getString("PBCustomerID"),
						items,
						r2.getDouble("PBSum"));
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return bill;
	}

}
