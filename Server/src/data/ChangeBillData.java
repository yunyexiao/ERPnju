package data;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import dataservice.ChangeBillDataService;
import po.billpo.ChangeBillPO;
import po.billpo.ChangeItem;

public class ChangeBillData extends UnicastRemoteObject implements ChangeBillDataService{
	
    public ChangeBillData() throws RemoteException {
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
		ArrayList<ChangeItem> items=bill.getCommodityList();
		try{
			String tableName = bill.isOver() ? "InventoryOverflowBill" : "InventoryLostBill";
			boolean b1 = SQLQueryHelper.add(tableName, bill.getId()
					,bill.getOperator(),bill.getState()
					,bill.getDate() + " "+bill.getTime());
			boolean b2 = true;
			for(int i=0;i<items.size();i++){
				b2 = b2 && SQLQueryHelper.add(tableName, bill.getAllId(),items.get(i).getCommodityId()
						,items.get(i).getOriginalValue(),items.get(i).getChangedValue());
			}
			if(b1 && b2) return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return false;
	}

}
