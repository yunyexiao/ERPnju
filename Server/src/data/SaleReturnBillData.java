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
			Statement s1 = DataHelper.getInstance().createStatement();
			int r1=s1.executeUpdate("INSERT INTO SalesReturnBill VALUES"
					+ "('"
					+bill.getId()+"','"
					+bill.getCustomerId()+"','"
					+bill.getSalesManName()+"','"
					+bill.getOperator()+"','"
					+bill.getOriginalSum()+"','"
					+bill.getReturnSum()+"','"
					+bill.getRemark()+"',' "
					+bill.getOriginalSBId()+"','"
					+bill.getDate()+" "+bill.getTime()+"','"
					+bill.getState()+"')");
			
			for(int i=0;i<items.size();i++){
			Statement s2 = DataHelper.getInstance().createStatement();
			int r2=s2.executeUpdate("INSERT INTO SalesReturnRecord VALUES ('"
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
	public boolean deleteBill(String id) throws RemoteException {
		
        boolean res=SQLQueryHelper.getTrueDeleteResult(billTableName, billIdName, id);
		return res;
	}

	@Override
	public String getNewId() throws RemoteException {
		
		String newId=SQLQueryHelper.getNewBillIdByDay(billTableName,billIdName);		
		return newId;		
	}

	@Override
	public SalesReturnBillPO getBillById(String id) throws RemoteException {
		ArrayList<SalesItemsPO> items=new ArrayList<SalesItemsPO>();
		SalesReturnBillPO bill=null;
		
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
				
			    bill=new SalesReturnBillPO(
			    		r2.getString("generateTime").split(" ")[0],
			    		r2.getString("generateTime").split(" ")[1],
						r2.getString("SRBID"),
						r2.getString("SRBOperatorID"),
						r2.getInt("SRBCondition"),
						r2.getString("SRBCustomerID"),
						r2.getString("SRBSalesmanName"),
						r2.getString("SRBRemark"),
						r2.getString("SRBOriginalSBID"),
						r2.getDouble("SRBOriginalSum"),
						r2.getDouble("SRBReturnSum"),
						items);
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		return bill;

	}

	@Override
	public ArrayList<SalesReturnBillPO> getBillsByDate(String from, String to) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<SalesReturnBillPO> bills=new ArrayList<SalesReturnBillPO>();
		try{
			Statement s=DataHelper.getInstance().createStatement();
			ResultSet r=s.executeQuery("SELECT * FROM "+billTableName+
					" WHERE generateTime>'"+from+"' AND generateTime<DATEADD(DAY,1,"+"'"+to+"');");
			while(r.next()){
				SalesReturnBillPO bill=new SalesReturnBillPO();
				bill.setCustomerId(r.getString("SRBCustomerID"));
				bill.setDate(r.getString("generateTime").split(" ")[0]);
				bill.setId(r.getString("SRBID"));
				bill.setOperatorId(r.getString("SRBOperatorID"));
				bill.setOriginalSBId(r.getString("SRBOriginalSBID"));
				bill.setOriginalSum(r.getDouble("SRBReturnSum"));
				bill.setRemark(r.getString("SRBRemark"));
				bill.setReturnSum(r.getDouble("SRBReturnSum"));
				bill.setSalesManName(r.getString("SRBSalesmanName"));
				bill.setState(r.getInt("SRBCondition"));
				bill.setTime(r.getString("generateTime").split(" ")[1]);				
			}
			
			for(int i=0;i<bills.size();i++){
				Statement s1=DataHelper.getInstance().createStatement();
				ResultSet r1=s1.executeQuery("SELECT * FROM "+recordTableName+
						" WHERE SRRID="+bills.get(i).getId()+";");
				ArrayList<SalesItemsPO> items=new ArrayList<SalesItemsPO>();
				
				while(r1.next()){
					SalesItemsPO item=new SalesItemsPO(
							r1.getString("SRRComID"),
							r1.getString("SRRRemark"),
							r1.getInt("SRRComQuantity"),
							r1.getDouble("SRRPrice"),
							r1.getDouble("SRRComSum"));
					items.add(item);
				}
				
				bills.get(i).setSalesReturnBillItems(items);;

			}

		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return bills;
	}

}
