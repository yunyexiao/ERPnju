package data;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import dataservice.SalesReturnBillDataService;
import po.billpo.SalesItemsPO;
import po.billpo.SalesReturnBillPO;
public class SaleReturnBillData extends UnicastRemoteObject implements SalesReturnBillDataService{
	private String billTableName="SalesReturnBill";
	private String recordTableName="SalesReturnRecord";
	private String billIdName="SRBID";
	private String recordIdName="SRRID";


	public SaleReturnBillData() throws RemoteException {
		super();

	}

	@Override
	public boolean saveBill(SalesReturnBillPO bill) throws RemoteException {
		ArrayList<SalesItemsPO> items=bill.getSalesReturnBillItems();
		try{
			boolean b1 = SQLQueryHelper.add(billTableName, bill.getAllId()
					,bill.getCustomerId(),bill.getSalesManName(),bill.getOperator(),bill.getOriginalSum()
					,bill.getReturnSum(),bill.getRemark()
					,bill.getOriginalSBId(),bill.getDate() + " "+bill.getTime(),bill.getState());
			boolean b2 = true;
			for(int i=0;i<items.size();i++){
				b2 = b2 && SQLQueryHelper.add(recordTableName, bill.getAllId()
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
		return BillDataHelper.getNewBillId(billTableName,billIdName);		
	}

	@Override
	public SalesReturnBillPO getBillById(String id) throws RemoteException {
		return BillDataHelper.getSalesReturnBill(id);
	}

	@Override
	public ArrayList<SalesReturnBillPO> getBillsByDate(String from, String to) throws RemoteException {
		ArrayList<SalesReturnBillPO> bills=new ArrayList<SalesReturnBillPO>();
		try{
			Statement s=DataHelper.getInstance().createStatement();
			ResultSet r=s.executeQuery("SELECT * FROM "+billTableName+
					" WHERE generateTime>'"+from+"' AND generateTime<DATEADD(DAY,1,"+"'"+to+"');");
			while(r.next()) bills.add(BillDataHelper.getSalesReturnBill(r.getString("SRBID")));
			return bills;
		}catch(Exception e){
			e.printStackTrace();
			return bills;
		}
	}
}
