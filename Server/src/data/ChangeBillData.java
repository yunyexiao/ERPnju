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
		
		String billName = bill.isOver() ? "InventoryOverflowBill" : "InventoryLostBill";
		String recordName=bill.isOver() ? "InventoryOverflowRecord" : "InventoryLostRecord";
		String[] billAttributes = new String[4];
		String[] recordAttributes=new String[4];
		Object[] billValues=new Object[4];
		Object[] recordValues=new Object[4];
		if(bill.isOver()){
			billAttributes[0]="IOBID";
			billAttributes[1]="IOBOperatorID";
			billAttributes[2]="IOBCondition";
			billAttributes[3]="generateTime";
			
			recordAttributes[0]="IORID";
			recordAttributes[1]="IORComID";
			recordAttributes[2]="IORComQuantity";
			recordAttributes[3]="IOROverQuantity";	
		}
		
		else{
			billAttributes[0]="ILBID";
			billAttributes[1]="ILBOperatorID";
			billAttributes[2]="ILBCondition";
			billAttributes[3]="generateTime";
			
			recordAttributes[0]="ILRID";
			recordAttributes[1]="ILRComID";
			recordAttributes[2]="ILRComQuantity";
			recordAttributes[3]="ILRLostQuantity";				
		}
		billValues[0]=bill.getId();
		billValues[1]=bill.getOperator();
		billValues[2]=bill.getState();
		billValues[3]=bill.getDate()+" "+bill.getTime();
		
		try{
			boolean isExist=BillDataHelper.isBillExist(billName, billAttributes[0], bill);
			
			if(!isExist){
			boolean b1 = SQLQueryHelper.add(billName, billValues);
			boolean b2 = true;
			for(int i=0;i<items.size();i++){
				b2 = b2 && SQLQueryHelper.add(billName, bill.getAllId(),items.get(i).getCommodityId()
						,items.get(i).getOriginalValue(),items.get(i).getChangedValue());
			}
			return b1 && b2;
			}
			
			else{
				boolean b1=SQLQueryHelper.update(billName, billAttributes, billValues);
				boolean b2=false;
				for(int i=0;i<items.size();i++){
					recordValues[0]=bill.getId();
					recordValues[1]=items.get(i).getCommodityId();
					recordValues[2]=items.get(i).getOriginalValue();
					recordValues[3]=items.get(i).getChangedValue();
					
					b2=b2||SQLQueryHelper.update(recordName, recordAttributes, recordValues);
				}
				return b1||b2;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
