package data;

import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

import dataservice.BillSearchDataService;
import po.UserPO;
import po.billpo.*;

public class BillSearchData implements BillSearchDataService{
	//查找所有状态的单据传state参数-1；
	@Override
	public ArrayList<PurchaseBillPO> searchPurchaseBills(String fromDate, String toDate, String customerId,
			String operatorId,int state) throws RemoteException {

		ArrayList<PurchaseBillPO> bills=new ArrayList<PurchaseBillPO>();
		String sql=assembleSQL("PurchaseBill", fromDate, toDate, 
				"PBSupplierID", customerId, "PBOperatorID", operatorId,"PBCondition",state);
			
		try{
			Statement s=DataHelper.getInstance().createStatement();
			ResultSet r=s.executeQuery(sql);
			while(r.next()){
				Statement s1=DataHelper.getInstance().createStatement();
				ResultSet r1=s1.executeQuery("SELECT * FROM PurchaseRecord WHERE PRID="+r.getString("PBID")+";");
				ArrayList<SalesItemsPO> items=new ArrayList<SalesItemsPO>();
				
				while(r1.next()){
					SalesItemsPO item=new SalesItemsPO(
							r1.getString("PRComID"),
							r1.getString("PRRemark"),
							r1.getInt("PRComQuantity"),
							r1.getDouble("PRComPrice"),
							r1.getDouble("PRComSum"));
					items.add(item);
				}

				PurchaseBillPO bill=new PurchaseBillPO(
						r.getString("generateTime").split(" ")[0],
						r.getString("generateTime").split(" ")[1],
						r.getString("PBID"),
						r.getString("PBOperatorID"),
						r.getInt("PBCondition"),
						r.getString("PBSupplierID"),
						r.getString("PBRemark"),
						r.getDouble("PBSum"),
						items);
			
				bills.add(bill);				
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return bills;
	}

	@Override
	public ArrayList<PurchaseReturnBillPO> searchPurchaseReturnBills(String fromDate, String toDate, String customerId,
			String operatorId,int state) throws RemoteException {

		ArrayList<PurchaseReturnBillPO> bills=new ArrayList<PurchaseReturnBillPO>();
		String sql=assembleSQL("PurchaseReturnBill", fromDate, toDate, 
				"PRBSupplierID", customerId, "PRBOperatorID", operatorId,"PRBCondition",state);
		try{
			Statement s=DataHelper.getInstance().createStatement();
			ResultSet r=s.executeQuery(sql);
			
			while(r.next()){
				Statement s1=DataHelper.getInstance().createStatement();
				ResultSet r1=s1.executeQuery("SELECT * FROM PurchaseReturnRecord WHERE PRRID="+r.getString("PRBID")+";");
				ArrayList<SalesItemsPO> items=new ArrayList<SalesItemsPO>();
				
				while(r1.next()){
					SalesItemsPO item=new SalesItemsPO(
							r1.getString("PRRComID"),
							r1.getString("PRRRemark"),
							r1.getInt("PRRComQuantity"),
							r1.getDouble("PRRComPrice"),
							r1.getDouble("PRRComSum"));
					items.add(item);
				}

				PurchaseReturnBillPO bill=new PurchaseReturnBillPO(
						r.getString("generateTime").split(" ")[0],
						r.getString("generateTime").split(" ")[1],
						r.getString("PRBID"),
						r.getString("PRBOperatorID"),
						r.getInt("PRBCondition"),
						r.getString("PRBSupplierID"),
						r.getString("PRBRemark"),
						r.getDouble("PRBSum"),
						items);
				
				bills.add(bill);
				}			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return bills;
	}

	@Override
	public ArrayList<SalesBillPO> searchSalesBills(String fromDate, String toDate, String customerId, String operatorId,int state)
			throws RemoteException {
		ArrayList<SalesBillPO> bills=new ArrayList<SalesBillPO>();
		String sql=assembleSQL("SalesBill", fromDate, toDate, 
				"SBCustomerID", customerId, "SBOperatorID", operatorId,"SBCondition",state);
		
		try{
			
			Statement s=DataHelper.getInstance().createStatement();
			ResultSet r=s.executeQuery(sql);
			while(r.next()){
				Statement s1=DataHelper.getInstance().createStatement();
				ResultSet r1=s1.executeQuery("SELECT * FROM SalesRecord WHERE SRID="+r.getString("SBID")+";");
				ArrayList<SalesItemsPO> items=new ArrayList<SalesItemsPO>();
				
				while(r1.next()){
					SalesItemsPO item=new SalesItemsPO(
							r1.getString("SRComID"),
							r1.getString("SRRemark"),
							r1.getInt("SRComQuantity"),
							r1.getDouble("SRPrice"),
							r1.getDouble("SRComSum"));
					items.add(item);
				}

				SalesBillPO bill=new SalesBillPO(
						r.getString("generateTime").split(" ")[0],
						r.getString("generateTime").split(" ")[1],
						r.getString("SBID"),
						r.getString("SBOperatorID"),
						r.getInt("PBCondition"),
						r.getString("SBCustomerID"),
						r.getString("SBSalesmanName"),
						r.getString("SBRemark"),
						r.getString("SBPromotionID"),
						r.getDouble("SBBeforeDiscount"),
						r.getDouble("SBDiscount"),
						r.getDouble("SBCoupon"),
						r.getDouble("SBAfterDiscount"),
						items);
			
				bills.add(bill);
			}			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return bills;

	}

	@Override
	public ArrayList<SalesReturnBillPO> searchSalesReturnBills(String fromDate, String toDate, String customerId,
			String operatorId,int state) throws RemoteException {
		
		ArrayList<SalesReturnBillPO> bills=new ArrayList<SalesReturnBillPO>();
		String sql=assembleSQL("SalesReturnBill", fromDate, toDate, 
				"SRBCustomerID", customerId, "SRBOperatorID", operatorId,"SRBCondition",state);
		try{
			Statement s=DataHelper.getInstance().createStatement();
			ResultSet r=s.executeQuery(sql);
			while(r.next()){
				Statement s1=DataHelper.getInstance().createStatement();
				ResultSet r1=s1.executeQuery("SELECT * FROM SalesReturnRecord WHERE SRRID="+r.getString("SRBID")+";");
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

				SalesReturnBillPO bill=new SalesReturnBillPO(
						r.getString("generateTime").split(" ")[0],
						r.getString("generateTime").split(" ")[1],
						r.getString("SRBID"),
						r.getString("SRBOperatorID"),
						r.getInt("SRBCondition"),
						r.getString("SRBCustomerID"),
						r.getString("SRBSalesmanName"),
						r.getString("SRBRemark"),
						r.getString("SRBOriginalSBID"),
						r.getDouble("SRBOriginalSum"),
						r.getDouble("SRBReturnSum"),
						items);
				
				bills.add(bill);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return bills;
	}

	@Override
	public ArrayList<CashCostBillPO> searchCashCostBills(String fromDate, String toDate, String customerId,
			String operatorId,int state) throws RemoteException {
		ArrayList<CashCostBillPO> bills=new ArrayList<CashCostBillPO>();
		String sql=assembleSQL("CashCostBill", fromDate, toDate, 
				"CCBAccountID", customerId, "CCBOperatorID", operatorId,"CCBCondition",state);
		try{
			Statement s=DataHelper.getInstance().createStatement();
			ResultSet r=s.executeQuery(sql);
			
			while(r.next()){
				Statement s1=DataHelper.getInstance().createStatement();
				ResultSet r1=s1.executeQuery("SELECT * FROM CashCostRecord WHERE CCRID="+r.getString("CCBID")+";");
				ArrayList<CashCostItem> items=new ArrayList<CashCostItem>();

				while(r1.next()){
					CashCostItem item=new CashCostItem(
							r1.getString("CCRCostName"),
							r1.getDouble("CCRMoney"),
							r1.getString("CCRRemark"));
					items.add(item);
				}

				CashCostBillPO bill=new CashCostBillPO(
						r.getString("generateTime").split(" ")[0],
						r.getString("generateTime").split(" ")[1],
						r.getString("CCBID"),
						r.getString("CCBOperatorID"),
						r.getInt("CCBCondition"),
						r.getString("CCBAccountID"),
						items,
						r.getDouble("CCBSum"));
				
				bills.add(bill);
			}				
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		return bills;
	}

	@Override
	public ArrayList<PaymentBillPO> searchPaymentBills(String fromDate, String toDate, String customerId,
			String operatorId, int state) throws RemoteException {
		
		ArrayList<PaymentBillPO> bills=new ArrayList<PaymentBillPO>();
		String sql=assembleSQL("PaymentBill", fromDate, toDate, 
				"PBCustomerID", customerId, "PBOperatorID", operatorId,"PBCondition",state);
		
		try{
			Statement s=DataHelper.getInstance().createStatement();
			ResultSet r=s.executeQuery(sql);
			
			while(r.next()){
				Statement s2=DataHelper.getInstance().createStatement();
				ResultSet r2=s2.executeQuery("SELECT * FROM PaymentRecord WHERE PRID="+r.getString("PBID")+";");
								
				ArrayList<TransferItem> items=new ArrayList<TransferItem>();
				
				while(r2.next()){
					TransferItem item=new TransferItem(
							r2.getString("PRAccountID"),
							r2.getDouble("PRTransfer"),
							r2.getString("PRRemark"));
					items.add(item);
				}
				
				PaymentBillPO bill=new PaymentBillPO(
						r.getString("generateTime").split(" ")[0],
						r.getString("generateTime").split(" ")[1],
						r.getString("PBID"),
						r.getString("PBOperatorID"),
						r.getInt("PBCondition"),
						r.getString("PBCustomerID"),
						items,
						r.getDouble("PBSum"));
				
				bills.add(bill);
			}

		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		return bills;
	}

	@Override
	public ArrayList<ReceiptBillPO> searchReceiptBills(String fromDate, String toDate, String customerId,
			String operatorId, int state) throws RemoteException {
		ArrayList<ReceiptBillPO> bills=new ArrayList<ReceiptBillPO>();
		String sql=assembleSQL("ReceiptBill", fromDate, toDate, 
				"RBCustomerID", customerId, "RBOperatorID", operatorId,"RBCondition",state);
		
		try{
			Statement s=DataHelper.getInstance().createStatement();
			ResultSet r=s.executeQuery(sql);
			
			while(r.next()){
				Statement s2=DataHelper.getInstance().createStatement();
				ResultSet r2=s2.executeQuery("SELECT * FROM ReceiptRecord WHERE RRID="+r.getString("RBID")+";");
								
				ArrayList<TransferItem> items=new ArrayList<TransferItem>();
				
				while(r2.next()){
					TransferItem item=new TransferItem(
							r2.getString("RRAccountID"),
							r2.getDouble("RRTransfer"),
							r2.getString("RRRemark"));
					items.add(item);
				}
				
				ReceiptBillPO bill=new ReceiptBillPO(
						r.getString("generateTime").split(" ")[0],
						r.getString("generateTime").split(" ")[1],
						r.getString("PBID"),
						r.getString("PBOperatorID"),
						r.getInt("PBCondition"),
						r.getString("PBCustomerID"),
						items,
						r.getDouble("PBSum"));
				
				bills.add(bill);
			}

		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		return bills;
	}
	
		@Override
	public ArrayList<ChangeBillPO> searchChangeBills(String fromDate, String toDate, String store, String operatorId,
			boolean isOver,int state) throws RemoteException {
			
			ArrayList<ChangeBillPO> bills=new ArrayList<ChangeBillPO>();
			String operator1=" AND IOBOperatorID='";
			String operator2=" AND ILBOperatorID='";
			
			if(operatorId==null)
			{
				operator1=null;
				operator2=null;
			}
							
			try{
				
			if(isOver){
				if(store==null&&state!=-1){
					String sql="SELECT * FROM InventoryOverflowBill WHERE generateTime>'"+fromDate
							+"' AND generateTime<DATEADD(DAY,1,'"+toDate+"')"
							+operator1+operatorId
							+"' AND IOBCondition="+state+";";
					bills=getChangeBills(sql,true);
			    }
				else if(store!=null&&state!=-1){
					String sql="SELECT DISTINCT IOBID, IOBOperatorID, IOBCondition, generateTime"
							+ " FROM InventoryOverflowRecord, CommodityInfo, InventoryOverflowBill"
							+ " WHERE IORComID=ComID AND IOBID=IORID"
							+ " AND ComStore='"+store
							+"' AND generateTime>'"+fromDate
							+"' AND generateTime<DATEADD(DAY,1,'"+toDate+"')"
							+operator1+operatorId
							+"' AND IOBCondition="+state+";";
					bills=getChangeBills(sql,true);
				}
				
				else if(store==null&&state==-1){
					String sql="SELECT * FROM InventoryOverflowBill WHERE generateTime>'"+fromDate
							+"' AND generateTime<DATEADD(DAY,1,'"+toDate+"')"
							+operator1+operatorId+"';";
					bills=getChangeBills(sql,true);
				}
				else if(store!=null&&state==-1){
					String sql="SELECT DISTINCT IOBID, IOBOperatorID, IOBCondition, generateTime"
							+ " FROM InventoryOverflowRecord, CommodityInfo, InventoryOverflowBill"
							+ " WHERE IORComID=ComID AND IOBID=IORID"
							+ " AND ComStore='"+store
							+operator1+operatorId
							+"' AND generateTime>'"+fromDate
							+"' AND generateTime<DATEADD(DAY,1,'"+toDate+"');";
					
					bills=getChangeBills(sql,true);					
				}

			}
			else if(!isOver){
				
				if(store==null&&state!=-1){
					String sql="SELECT * FROM InventoryLostBill WHERE generateTime>'"+fromDate
							+"' AND generateTime<DATEADD(DAY,1,'"+toDate+"')"
							+operator2+operatorId
							+"' AND ILBCondition="+state+";";
					bills=getChangeBills(sql,false);
				}
				else if(store!=null&&state!=-1){
					String sql="SELECT DISTINCT ILBID, ILBOperatorID, ILBCondition, generateTime"
							+ " FROM InventoryLostRecord, CommodityInfo, InventoryLostBill"
							+ " WHERE ILRComID=ComID AND ILBID=ILRID"
							+ " AND ComStore='"+store
							+"' AND generateTime>'"+fromDate
							+"' AND generateTime<DATEADD(DAY,1,'"+toDate+"')"
							+operator2+operatorId
							+"' AND ILBCondition="+state+";";
					bills=getChangeBills(sql,false);													
				}
				
				else if(store==null&&state==-1){
					String sql="SELECT * FROM InventoryLostBill WHERE generateTime>'"+fromDate
							+"' AND generateTime<DATEADD(DAY,1,'"+toDate+"')"
							+operator2+operatorId+"';";
					
					bills=getChangeBills(sql,false);
				}
				else if(store!=null&&state==-1){
					String sql="SELECT DISTINCT IOBID, IOBOperatorID, IOBCondition, generateTime"
							+ " FROM InventoryLostRecord, CommodityInfo, InventoryLostBill"
							+ " WHERE ILRComID=ComID AND ILBID=ILRID AND ComStore='"+store
							+"' AND ILBOperatorID='"+operatorId
							+"' AND generateTime>'"+fromDate
							+"' AND generateTime<DATEADD(DAY,1,'"+toDate+"')"
							+operator2+operatorId+"';";
					
					bills=getChangeBills(sql,false);
				}		
			}	
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}	
		return bills;
	}

	private String assembleSQL(String tableName, String fromDate, String toDate, 
			String optional1_Name, String optional1, String optional2_Name, String optional2,
			String condition_Name,int condition){
		
		String sql="";
		String op1_And=" AND ",op2_And="' AND ",op1_equ="='",op2_equ="='";
		
		if(optional1==null){
			optional1_Name=null;
			op1_And=null;
			op1_equ=null;
		}
		
		if(optional2==null){
			optional2_Name=null;
			op2_And=null;
			op2_equ=null;
		}
		
		if(condition==-1)
		{
			sql="SELECT * FROM "+tableName+" WHERE generateTime>'"+fromDate
			        +"' AND generateTime<DATEADD(DAY,1,"+"'"+toDate+"')"+
			        op1_And+optional1_Name+op1_equ+optional1
			        +op2_And+optional2_Name+op2_equ+optional2
			        +";";			
		}
		else{
			sql="SELECT * FROM "+tableName+" WHERE generateTime>'"+fromDate
			        +"' AND generateTime<DATEADD(DAY,1,"+"'"+toDate+"')"+
			        op1_And+optional1_Name+op1_equ+optional1
			        +op2_And+optional2_Name+op2_equ+optional2
			        +"' AND "+condition_Name+"="+condition+";";			
		}
		/*
		if(optional1==null&&optional2!=null)
			sql="SELECT * FROM "+tableName+" WHERE generateTime>'"+fromDate
			        +"' AND generateTime<DATEADD(DAY,1,"+"'"+toDate
			        +"') AND "+optional2_Name+"='"+optional2
			        +"' AND "+condition_Name+"="+condition+";";
		else if(optional1!=null&&optional2==null)
			sql="SELECT * FROM "+tableName+" WHERE generateTime>'"+fromDate
					+"' AND generateTime<DATEADD(DAY,1,"+"'"+toDate+
					"') AND "+optional1_Name+"='"+optional1
					+"' AND "+condition_Name+"="+condition+";";
		else if(optional1==null&&optional2==null)
			sql="SELECT * FROM "+tableName+" WHERE generateTime>'"+fromDate
			        +"' AND generateTime<DATEADD(DAY,1,"+"'"+toDate
			        +"' AND "+condition_Name+"="+condition+";";
		else if(optional1!=null&&optional2!=null)
			sql="SELECT * FROM "+tableName+" WHERE generateTime>'"+fromDate
			        +"' AND generateTime<DATEADD(DAY,1,"+"'"+toDate+
			        "') AND "+optional1_Name+"='"+optional1
			        +"' AND "+optional2_Name+"='"+optional2
			        +"' AND "+condition_Name+"="+condition+";";
	      */

		return sql;
		
	}
	
	private ArrayList<ChangeBillPO> getChangeBills(String sql1,boolean isOver){
		
		String[] billAttributes=new  String[4];
		String[] recordAttributes=new String[3];
		String sql2=null;
		ArrayList<ChangeBillPO> bills=new ArrayList<ChangeBillPO>();
		
		billAttributes[0]="generateTime";
		
		if(isOver){
			billAttributes[1]="IOBID";
			billAttributes[2]="IOBOperatorID";
			billAttributes[3]="IOBCondition";
			
			recordAttributes[0]="IORComID";
			recordAttributes[1]="IORComQuantity";
			recordAttributes[2]="IOROverQuantity";
			
			sql2="SELECT * FROM InventoryOverflowRecord WHERE IORID='";
		}
		else if(!isOver){
			billAttributes[1]="ILBID";
			billAttributes[2]="ILBOperatorID";
			billAttributes[3]="ILBCondition";
			
			recordAttributes[0]="ILRComID";
			recordAttributes[1]="ILRComQuantity";
			recordAttributes[2]="ILRLostQuantity";
			
			sql2="SELECT * FROM InventoryLostRecord WHERE ILRID='";
		}
		
		try{
			Statement s1=DataHelper.getInstance().createStatement();
			ResultSet r1=s1.executeQuery(sql1);
			
			while(r1.next()){
				Statement s2=DataHelper.getInstance().createStatement();
				ResultSet r2=s2.executeQuery(sql2+r1.getString(billAttributes[1])+"';");

				ArrayList<ChangeItem> items=new ArrayList<ChangeItem>();
				while(r2.next()){
					ChangeItem item=new ChangeItem(
							r2.getString(recordAttributes[0]),
							r2.getInt(recordAttributes[1]),
							r2.getInt(recordAttributes[2]));
					items.add(item);	
				}
				
				ChangeBillPO bill=new ChangeBillPO(
						r1.getString(billAttributes[0]).split(" ")[0],
						r1.getString(billAttributes[0]).split(" ")[1],
						r1.getString(billAttributes[1]),
						r1.getString(billAttributes[2]),
						r1.getInt(billAttributes[3]),
						true,
						items);
				bills.add(bill);
				}							

		}catch(Exception e){
			e.printStackTrace();
			return null;
		}		
		return bills;		
	}

	@Override
	public ArrayList<BillPO> getBillList(UserPO user) throws RemoteException {

		ArrayList<BillPO> bills=new ArrayList<BillPO>();

		Calendar now = Calendar.getInstance(); 
		String today=now.get(Calendar.YEAR)+"-"+now.get(Calendar.MONTH)+"-"+now.get(Calendar.DAY_OF_MONTH);
		String start="2000-01-01";
		String userId=user.getUserId();
	
		if(user.getUsertype()==0){
			try{
				ArrayList<ChangeBillPO> lostBills=searchChangeBills(start, today, null, userId,false, -1);
				ArrayList<ChangeBillPO> overflowBills=searchChangeBills(start, today, null, userId, true, -1);
				bills.addAll(lostBills);
				bills.addAll(overflowBills);	
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
		}
		//销售人员
		else if(user.getUsertype()==1){
			ArrayList<SalesBillPO> salesBills=searchSalesBills(start, today, null, userId, -1);
			ArrayList<SalesReturnBillPO> salesReturnBills=searchSalesReturnBills(start, today, null, userId, -1);
			ArrayList<PurchaseBillPO> purchaseBills=searchPurchaseBills(start, today, null, userId, -1);
			ArrayList<PurchaseReturnBillPO> purchaseReturnBills=searchPurchaseReturnBills(start, today, null, userId, -1);
			bills.addAll(purchaseReturnBills);
			bills.addAll(purchaseBills);
			bills.addAll(salesReturnBills);
			bills.addAll(salesBills);
		}
		//财务人员
		else if(user.getUsertype()==2){
			ArrayList<CashCostBillPO> cashCostBills=searchCashCostBills(start, today, null, userId, -1);
			ArrayList<PaymentBillPO> paymentBills=searchPaymentBills(start, today, null, userId, -1);
			ArrayList<ReceiptBillPO> receiptBills=searchReceiptBills(start, today, null, userId, -1);
			bills.addAll(receiptBills);
			bills.addAll(cashCostBills);
			bills.addAll(paymentBills);
		}
		return bills;
	}


}
