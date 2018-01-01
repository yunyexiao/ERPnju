package data;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dataservice.SalesBillDataService;
import po.billpo.SalesBillPO;
import po.billpo.SalesItemsPO;
public class SalesBillData extends UnicastRemoteObject implements SalesBillDataService{
	private static final long serialVersionUID = -952253035895433810L;
	private String billName="SalesBill";
	private String recordName="SalesRecord";
	private String[] billAttributes={"SBID","SBCustomerID","SBSalesmanName","SBOperatorID","SBBeforeDiscount",
			"SBDiscount","SBCoupon","SBAfterDiscount","SBRemark","SBPromotionID","generateTime","SBCondition"};
	private String[] recordAttributes={"SRID","SRComID","SRComQuantity","SRPrice","SRComSum","SRRemark"};

	public SalesBillData() throws RemoteException {
		super();
		
	}

	@Override
	public boolean saveBill(SalesBillPO bill) throws RemoteException {
		ArrayList<SalesItemsPO> items = bill.getSalesBillItems();
		boolean isExist=BillDataHelper.isBillExist(billName, billAttributes[0], bill);
		Object[] billValues={bill.getAllId(), bill.getCustomerId(), bill.getSalesManName(),
				bill.getOperator(), bill.getBeforeDiscount(), bill.getDiscount(),
				bill.getCoupon(), bill.getAfterDiscount(), bill.getRemark(),
				bill.getPromotionId(), bill.getDate() + " "+bill.getTime(), bill.getState()};

		try{
			if(!isExist){
			boolean b1 = SQLQueryHelper.add(billName, billValues);
			boolean b2 = true;
			for(int i=0;i<items.size();i++){
				b2 = b2 && SQLQueryHelper.add(recordName, bill.getAllId()
						,items.get(i).getComId(),items.get(i).getComQuantity(),items.get(i).getComSum()
						,items.get(i).getComRemark(),items.get(i).getComPrice());
			}
			return b1&&b2;
			}
			else{
				boolean b1=SQLQueryHelper.update(billName, billAttributes, billValues);
				boolean b2=false;
				for(int i=0;i<items.size();i++){
					Object[] recordValues={bill.getAllId(), items.get(i).getComId(), items.get(i).getComQuantity(), 
							items.get(i).getComSum(), items.get(i).getComRemark(), items.get(i).getComPrice()};
					b2=b2||SQLQueryHelper.update(recordName, recordAttributes, recordValues);
				}
				return b1||b2;
			}
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
		return BillDataHelper.getNewBillId(billName, billAttributes[0]);		
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
				res_Bill =SQLQueryHelper.getRecordByAttribute(billName, field, key);
				res_Record =SQLQueryHelper.getRecordByAttribute(recordName, field, key);
			}
			
			List<String> tempList = Arrays.asList(billAttributes);
			if(tempList.contains(field)){
				ArrayList<String> ids=new ArrayList<String>();
				while(res_Record.next()){
					boolean isAdd=true;
					for(int i=0;i<ids.size();i++)
						if(res_Record.getString(recordAttributes[0])==ids.get(i))isAdd=false;
					if(isAdd)ids.add(res_Record.getString(recordAttributes[0]));
				}
				for(int i = 0; i < ids.size(); i++) bills.add(BillDataHelper.getSalesBill(ids.get(i)));
			}
			else while(res_Bill.next()) bills.add(BillDataHelper.getSalesBill(res_Bill.getString(billAttributes[0])));	
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
			ResultSet r=s.executeQuery("SELECT * FROM "+billName+
					" WHERE generateTime>'"+from+"' AND generateTime<DATEADD(DAY,1,"+"'"+to+"');");
			while(r.next()) bills.add(BillDataHelper.getSalesBill(r.getString(billAttributes[0])));
			return bills;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
}
