package data;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import po.billpo.ChangeBillPO;
import po.billpo.ChangeItem;
import dataservice.ChangeBillDataService;

public class ChangeBillData extends UnicastRemoteObject implements ChangeBillDataService{
	
    protected ChangeBillData() throws RemoteException {
		super();

	}

	@Override
	public ChangeBillPO getBillById(String id, boolean isOver) throws RemoteException{

    	ArrayList<ChangeItem> changeItems=new ArrayList<ChangeItem>();
    	ChangeBillPO changeBill=null;
    	
    	if(isOver){
    		
		try{
			Statement s1=DataHelper.getInstance().createStatement();
			ResultSet r1=s1.executeQuery("SELECT * FROM InventoryOverflowRecord WHERE IORID="+id+";");
			
			while(r1.next()){	
			ChangeItem item=new ChangeItem(r1.getString("IORID"),r1.getInt("IORComQuantity"),r1.getInt("IOROverQuantity"));
			changeItems.add(item);
			}
			
			Statement s2=DataHelper.getInstance().createStatement();
			ResultSet r2=s2.executeQuery("SELECT * FROM InventoryOverflowBill WHERE IOBID="+id+";");
			while(r2.next()){
				String date=null, time=null;
				
				date=r2.getString("generateTime").split(" ")[0];
				time=r2.getString("generateTime").split(" ")[1];
				
				changeBill=new ChangeBillPO(date,time,r2.getString("IOBID"),r2.getString("IOBOperatorID"),
						 r2.getInt("IOBCondition"),true,changeItems);
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return changeBill;
    	}
    	
    	else if(!isOver){
    		
    		try{
    			Statement s1=DataHelper.getInstance().createStatement();
    			ResultSet r1=s1.executeQuery("SELECT * FROM InventoryLostRecord WHERE ILRID="+id+";");
    			
    			while(r1.next()){	
    			ChangeItem item=new ChangeItem(r1.getString("ILRID"),r1.getInt("ILRComQuantity"),r1.getInt("ILRLostQuantity"));
    			changeItems.add(item);
    			}
    			
    			Statement s2=DataHelper.getInstance().createStatement();
    			ResultSet r2=s2.executeQuery("SELECT * FROM InventoryLostBill WHERE ILBID="+id+";");
    			while(r2.next()){
    				String date=null, time=null;
   
    				date=r2.getString("generateTime").split(" ")[0];
    				time=r2.getString("generateTime").split(" ")[1];
    				
    				changeBill=new ChangeBillPO(date,time,r2.getString("ILBID"),r2.getString("ILBOperatorID"),
    						 r2.getInt("ILBCondition"),false,changeItems);
    			}
    		}catch(Exception e){
    			e.printStackTrace();
    			return null;
    		}
    		return changeBill;
    		
    	}
    	
    	return changeBill;
	}

	

	
	@Override
	public String getNewId(boolean isOver) throws RemoteException{

		String newId=null;
		
		if(isOver){
			newId=SQLQueryHelper.getNewId("InventoryOverflowBill", "IOBID", "%08d");
		}
		else if(!isOver){
			newId=SQLQueryHelper.getNewId("InventoryLostBill", "ILBID", "%08d");
		}
		return newId;
	}


	@Override
	public boolean deleteBill(String id, boolean isOver) throws RemoteException {
		
		boolean res=false;
		if(isOver){
			res=SQLQueryHelper.getTrueDeleteResult("InventoryOverflowBill", "IOBID", id);
			return res;
		}
		else if(!isOver){
			res=SQLQueryHelper.getTrueDeleteResult("InventoryLostBill", "ILBID", id);
			return res;
		}	
		return false;
	}

	@Override
	public boolean saveBill(ChangeBillPO bill) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<ChangeItem> items=bill.getCommodityList();
		if(bill.isOver()){
			try{
				Statement s1 = DataHelper.getInstance().createStatement();
				int r1=s1.executeUpdate("INSERT INTO InventoryOverflowBill VALUES('"
						+String.format("%8d", bill.getId())+"','"
						+bill.getOperatorId()+"','"
						+bill.getState()+"','"
						+bill.getDate()+" "+bill.getTime()+"')");
				
				for(int i=0;i<items.size();i++){
				Statement s2 = DataHelper.getInstance().createStatement();
				int r2=s2.executeUpdate("INSERT INTO InventoryOverflowRecord VALUES ('"
						+String.format("%8d", bill.getId())+"','"
						+items.get(i).getCommodityId()+"','"
						+items.get(i).getOriginalValue()+"','"
						+items.get(i).getChangedValue()+"')");
				}
				if(r1>0)return true;
			}catch(Exception e){
				  e.printStackTrace();
				   return false;
			}
		}
		else if(!bill.isOver()){
			try{
				Statement s1 = DataHelper.getInstance().createStatement();
				int r1=s1.executeUpdate("INSERT INTO InventoryLostBill VALUES"
						+ "('"
						+String.format("%8d", bill.getId())+"','"
						+bill.getOperatorId()+"','"
						+bill.getState()+"','"
						+bill.getDate()+" "+bill.getTime()+"')");
				
				for(int i=0;i<items.size();i++){
					
				Statement s2 = DataHelper.getInstance().createStatement();
				
				int r2=s2.executeUpdate("INSERT INTO InventoryLostRecord VALUES ('"
						+String.format("%8d", bill.getId())+"','"
						+items.get(i).getCommodityId()+"','"
						+items.get(i).getOriginalValue()+"','"
						+items.get(i).getChangedValue()+"')");
				}
				if(r1>0)return true;
			}catch(Exception e){
				  e.printStackTrace();
				   return false;
			}
		}
		
		return false;
	}

}
