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
	
	public PurchaseReturnBillData() throws RemoteException {
		super();
		
	}

	private String tableName="PurchaseReturnBill";
	private String idName="PRBID";

	@Override
	public boolean saveBill(PurchaseReturnBillPO bill) throws RemoteException {
        ArrayList<SalesItemsPO> items = bill.getPurchaseReturnBillItems();
		
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
		return BillDataHelper.getNewBillId(tableName,idName);
	}

	@Override
	public PurchaseReturnBillPO getBillById(String id) throws RemoteException {
		return BillDataHelper.getPurchaseRetrunBill(id);
	}

	@Override
	public ArrayList<PurchaseReturnBillPO> getBillsByDate(String from, String to) throws RemoteException {
		ArrayList<PurchaseReturnBillPO> bills=new ArrayList<PurchaseReturnBillPO>();
		try{
			Statement s=DataHelper.getInstance().createStatement();
			ResultSet r=s.executeQuery("SELECT * FROM "+tableName+
					" WHERE generateTime>'"+from+"' AND generateTime<DATEADD(DAY,1,"+"'"+to+"');");
			while(r.next()) bills.add(BillDataHelper.getPurchaseRetrunBill(r.getString("PRBID")));
			return bills;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

}
