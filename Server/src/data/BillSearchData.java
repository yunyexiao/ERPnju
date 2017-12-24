package data;

import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;

import dataservice.BillSearchDataService;
import po.UserPO;
import po.billpo.*;
public class BillSearchData implements BillSearchDataService{

	@Override
	public ArrayList<PurchaseBillPO> searchPurchaseBills(String fromDate, String toDate, String customerId,
			String operatorId) throws RemoteException {

		ArrayList<PurchaseBillPO> bills=new ArrayList<PurchaseBillPO>();
		String sql=assembleSQL("PurchaseBill", fromDate, toDate, 
				"PBSupplierID", customerId, "PBOperatorID", operatorId,"PBCondition");
			
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
			String operatorId) throws RemoteException {

		ArrayList<PurchaseReturnBillPO> bills=new ArrayList<PurchaseReturnBillPO>();
		String sql=assembleSQL("PurchaseReturnBill", fromDate, toDate, 
				"PRBSupplierID", customerId, "PRBOperatorID", operatorId,"PRBCondition");
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
	public ArrayList<SalesBillPO> searchSalesBills(String fromDate, String toDate, String customerId, String operatorId)
			throws RemoteException {
		ArrayList<SalesBillPO> bills=new ArrayList<SalesBillPO>();
		String sql=assembleSQL("SalesBill", fromDate, toDate, 
				"SBCustomerID", customerId, "SBOperatorID", operatorId,"SBCondition");
		
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
			String operatorId) throws RemoteException {
		
		ArrayList<SalesReturnBillPO> bills=new ArrayList<SalesReturnBillPO>();
		String sql=assembleSQL("SalesReturnBill", fromDate, toDate, 
				"SRBCustomerID", customerId, "SRBOperatorID", operatorId,"SRBCondition");
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
			String operatorId) throws RemoteException {
		ArrayList<CashCostBillPO> bills=new ArrayList<CashCostBillPO>();
		String sql=assembleSQL("CashCostBill", fromDate, toDate, 
				"CCBAccountID", customerId, "CCBOperatorID", operatorId,"CCBCondition");
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
			String operatorId) throws RemoteException {
		ArrayList<PaymentBillPO> bills=new ArrayList<PaymentBillPO>();
		String sql=assembleSQL("PaymentBill", fromDate, toDate, 
				"PBCustomerID", customerId, "PBOperatorID", operatorId,"PBCondition");
		
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
			String operatorId) throws RemoteException {
		ArrayList<ReceiptBillPO> bills=new ArrayList<ReceiptBillPO>();
		String sql=assembleSQL("ReceiptBill", fromDate, toDate, 
				"RBCustomerID", customerId, "RBOperatorID", operatorId,"RBCondition");
		
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
			boolean isOver) throws RemoteException {
			ArrayList<ChangeBillPO> bills=new ArrayList<ChangeBillPO>();
			
			try{

			if(isOver){
				if(store==null&&operatorId!=null){
					Statement s=DataHelper.getInstance().createStatement();
					ResultSet r=s.executeQuery("SELECT * FROM InventoryOverflowBill WHERE generateTime>'"
							+fromDate+"' AND generateTime<DATEADD(DAY,1,'"+toDate
							+"') AND IOBOperatorID='"+operatorId
							+"' AND IOBCondition=3;");
					
					while(r.next()){
						Statement s1=DataHelper.getInstance().createStatement();
						ResultSet r1=s1.executeQuery("SELECT * FROM InventoryOverflowRecord WHERE IORID="+r.getString("IOBID")+";");
						ArrayList<ChangeItem> items=new ArrayList<ChangeItem>();
						
						while(r1.next()){
							ChangeItem item=new ChangeItem(
									r1.getString("IORComID"),
									r1.getInt("IORComQuantity"),
									r1.getInt("IOROverQuantity"));
							items.add(item);
						}

						ChangeBillPO bill=new ChangeBillPO(
								r.getString("generateTime").split(" ")[0],
								r.getString("generateTime").split(" ")[1],
								r.getString("IOBID"),
								r.getString("IOBOperatorID"),
								r.getInt("IOBCondition"),
								true,
								items);
						
						bills.add(bill);
					}					
			}
				else if(store!=null&&operatorId==null){
						
					Statement s1=DataHelper.getInstance().createStatement();
					ResultSet r1=s1.executeQuery("SELECT DISTINCT IOBID, IOBOperatorID, IOBCondition, generateTime"
							+ " FROM InventoryOverflowRecord, CommodityInfo, InventoryOverflowBill"
							+ " WHERE IORComID=ComID AND IOBID=IORID AND ComStore='"+store
							+"' AND generateTime>'"+fromDate
							+"' AND generateTime<DATEADD(DAY,1,'"+toDate
							+"' AND IOBCondition=3;");
					
					while(r1.next()){
						Statement s2=DataHelper.getInstance().createStatement();
						ResultSet r2=s2.executeQuery("SELECT * FROM InventoryOverflowRecord WHERE IORID="+r1.getString("IOBID")+";");

						ArrayList<ChangeItem> items=new ArrayList<ChangeItem>();
						while(r2.next()){
							ChangeItem item=new ChangeItem(
									r2.getString("IORComID"),
									r2.getInt("IORComQuantity"),
									r2.getInt("IOROverQuantity"));
							items.add(item);	
						}
						
						ChangeBillPO bill=new ChangeBillPO(
								r1.getString("generateTime").split(" ")[0],
								r1.getString("generateTime").split(" ")[1],
								r1.getString("IOBID"),
								r1.getString("IOBOperatorID"),
								r1.getInt("IOBCondition"),
								true,
								items);
						bills.add(bill);
						}
												
				}
				
				else if(store==null&&operatorId==null){
					Statement s=DataHelper.getInstance().createStatement();
					ResultSet r=s.executeQuery("SELECT * FROM InventoryOverflowBill WHERE generateTime>'"+fromDate
							+"' AND generateTime<DATEADD(DAY,1,'"+toDate
							+"' AND IOBCondition=3;");
					
					while(r.next()){
						Statement s1=DataHelper.getInstance().createStatement();
						ResultSet r1=s1.executeQuery("SELECT * FROM InventoryOverflowRecord WHERE IORID="+r.getString("IOBID")+";");
						ArrayList<ChangeItem> items=new ArrayList<ChangeItem>();
						
						while(r1.next()){
							ChangeItem item=new ChangeItem(
									r1.getString("IORComID"),
									r1.getInt("IORComQuantity"),
									r1.getInt("IOROverQuantity"));
							items.add(item);
						}

						ChangeBillPO bill=new ChangeBillPO(
								r.getString("generateTime").split(" ")[0],
								r.getString("generateTime").split(" ")[1],
								r.getString("IOBID"),
								r.getString("IOBOperatorID"),
								r.getInt("IOBCondition"),
								true,
								items);
						
						bills.add(bill);
					}										
				}
				else if(store!=null&&operatorId!=null){
					Statement s1=DataHelper.getInstance().createStatement();
					ResultSet r1=s1.executeQuery("SELECT DISTINCT IOBID, IOBOperatorID, IOBCondition, generateTime"
							+ " FROM InventoryOverflowRecord, CommodityInfo, InventoryOverflowBill"
							+ " WHERE IORComID=ComID AND IOBID=IORID AND ComStore='"+store
							+"' AND IOBOperatorID='"+operatorId
							+"' AND generateTime>'"+fromDate
							+"' AND generateTime<DATEADD(DAY,1,'"+toDate
							+"' AND IOBCondition=3;");
					
					while(r1.next()){
						Statement s2=DataHelper.getInstance().createStatement();
						ResultSet r2=s2.executeQuery("SELECT * FROM InventoryOverflowRecord WHERE IORID="+r1.getString("IOBID")+";");

						ArrayList<ChangeItem> items=new ArrayList<ChangeItem>();
						while(r2.next()){
							ChangeItem item=new ChangeItem(
									r2.getString("IORComID"),
									r2.getInt("IORComQuantity"),
									r2.getInt("IOROverQuantity"));
							items.add(item);	
						}
						
						ChangeBillPO bill=new ChangeBillPO(
								r1.getString("generateTime").split(" ")[0],
								r1.getString("generateTime").split(" ")[1],
								r1.getString("IOBID"),
								r1.getString("IOBOperatorID"),
								r1.getInt("IOBCondition"),
								true,
								items);
						bills.add(bill);
						}					
				}

			}
			else if(!isOver){
				
				if(store==null&&operatorId!=null){
					Statement s=DataHelper.getInstance().createStatement();
					ResultSet r=s.executeQuery("SELECT * FROM InventoryLostBill WHERE generateTime>'"
							+fromDate+"' AND generateTime<DATEADD(DAY,1,'"+toDate
							+"') AND ILBOperatorID='"+operatorId
							+"' AND ILBCondition=3;");
					
					while(r.next()){
						Statement s1=DataHelper.getInstance().createStatement();
						ResultSet r1=s1.executeQuery("SELECT * FROM InventoryLostRecord WHERE ILRID="+r.getString("ILBID")+";");
						ArrayList<ChangeItem> items=new ArrayList<ChangeItem>();
						
						while(r1.next()){
							ChangeItem item=new ChangeItem(
									r1.getString("ILRComID"),
									r1.getInt("ILRComQuantity"),
									r1.getInt("ILRLostQuantity"));
							items.add(item);
						}

						ChangeBillPO bill=new ChangeBillPO(
								r1.getString("generateTime").split(" ")[0],
								r1.getString("generateTime").split(" ")[1],
								r1.getString("ILBID"),
								r1.getString("ILBOperatorID"),
								r1.getInt("ILBCondition"),
								false,
								items);
							
						bills.add(bill);
					}					
				}
				else if(store!=null&&operatorId==null){
						
					Statement s1=DataHelper.getInstance().createStatement();
					ResultSet r1=s1.executeQuery("SELECT DISTINCT ILBID, ILBOperatorID, ILBCondition, generateTime"
							+ " FROM InventoryLostRecord, CommodityInfo, InventoryLostBill"
							+ " WHERE ILRComID=ComID AND ILBID=ILRID AND ComStore='"+store
							+"' AND generateTime>'"+fromDate
							+"' AND generateTime<DATEADD(DAY,1,'"+toDate
							+"' AND ILBCondition=3;");
					
					while(r1.next()){
						Statement s2=DataHelper.getInstance().createStatement();
						ResultSet r2=s2.executeQuery("SELECT * FROM InventoryLostRecord WHERE ILRID="+r1.getString("ILBID")+";");

						ArrayList<ChangeItem> items=new ArrayList<ChangeItem>();
						while(r2.next()){
							ChangeItem item=new ChangeItem(
									r2.getString("ILRComID"),
									r2.getInt("ILRComQuantity"),
									r2.getInt("ILRLostQuantity"));
							items.add(item);	
						}
						
						ChangeBillPO bill=new ChangeBillPO(
								r1.getString("generateTime").split(" ")[0],
								r1.getString("generateTime").split(" ")[1],
								r1.getString("ILBID"),
								r1.getString("ILBOperatorID"),
								r1.getInt("ILBCondition"),
								false,
								items);
						bills.add(bill);
						}
												
				}
				
				else if(store==null&&operatorId==null){
					Statement s=DataHelper.getInstance().createStatement();
					ResultSet r=s.executeQuery("SELECT * FROM InventoryLostBill WHERE generateTime>'"+fromDate
							+"' AND generateTime<DATEADD(DAY,1,'"+toDate
							+"' AND ILBCondition=3;");
					
					while(r.next()){
						Statement s1=DataHelper.getInstance().createStatement();
						ResultSet r1=s1.executeQuery("SELECT * FROM InventoryLostRecord WHERE ILRID="+r.getString("ILBID")+";");
						ArrayList<ChangeItem> items=new ArrayList<ChangeItem>();
						
						while(r1.next()){
							ChangeItem item=new ChangeItem(
									r1.getString("ILRComID"),
									r1.getInt("ILRComQuantity"),
									r1.getInt("ILRLostQuantity"));
							items.add(item);
						}

						ChangeBillPO bill=new ChangeBillPO(
								r1.getString("generateTime").split(" ")[0],
								r1.getString("generateTime").split(" ")[1],
								r1.getString("ILBID"),
								r1.getString("ILBOperatorID"),
								r1.getInt("ILBCondition"),
								false,
								items);
							
						bills.add(bill);
					}										
				}
				else if(store!=null&&operatorId!=null){
					Statement s1=DataHelper.getInstance().createStatement();
					ResultSet r1=s1.executeQuery("SELECT DISTINCT IOBID, IOBOperatorID, IOBCondition, generateTime"
							+ " FROM InventoryLostRecord, CommodityInfo, InventoryLostBill"
							+ " WHERE ILRComID=ComID AND ILBID=ILRID AND ComStore='"+store
							+"' AND ILBOperatorID='"+operatorId
							+"' AND generateTime>'"+fromDate
							+"' AND generateTime<DATEADD(DAY,1,'"+toDate
							+"' AND ILBCondition=3;");
					
					while(r1.next()){
						Statement s2=DataHelper.getInstance().createStatement();
						ResultSet r2=s2.executeQuery("SELECT * FROM InventoryLostRecord WHERE ILRID="+r1.getString("ILBID")+";");

						ArrayList<ChangeItem> items=new ArrayList<ChangeItem>();
						while(r2.next()){
							ChangeItem item=new ChangeItem(
									r2.getString("ILRComID"),
									r2.getInt("ILRComQuantity"),
									r2.getInt("ILRLostQuantity"));
							items.add(item);	
						}
						
						ChangeBillPO bill=new ChangeBillPO(
								r1.getString("generateTime").split(" ")[0],
								r1.getString("generateTime").split(" ")[1],
								r1.getString("ILBID"),
								r1.getString("ILBOperatorID"),
								r1.getInt("ILBCondition"),
								true,
								items);
						bills.add(bill);
						}					
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
			String condition_Name){
		String sql="";
		if(optional1==null&&optional2!=null)
			sql="SELECT * FROM "+tableName+" WHERE generateTime>'"+fromDate
			        +"' AND generateTime<DATEADD(DAY,1,"+"'"+toDate
			        +"') AND "+optional2_Name+"='"+optional2
			        +"' AND "+condition_Name+"=3;";
		else if(optional1!=null&&optional2==null)
			sql="SELECT * FROM "+tableName+" WHERE generateTime>'"+fromDate
					+"' AND generateTime<DATEADD(DAY,1,"+"'"+toDate+
					"') AND "+optional1_Name+"='"+optional1
					+"' AND "+condition_Name+"=3;";
		else if(optional1==null&&optional2==null)
			sql="SELECT * FROM "+tableName+" WHERE generateTime>'"+fromDate
			        +"' AND generateTime<DATEADD(DAY,1,"+"'"+toDate
			        +"' AND "+condition_Name+"=3;";
		else if(optional1!=null&&optional2!=null)
			sql="SELECT * FROM "+tableName+" WHERE generateTime>'"+fromDate
			        +"' AND generateTime<DATEADD(DAY,1,"+"'"+toDate+
			        "') AND "+optional1_Name+"='"+optional1
			        +"' AND "+optional2_Name+"='"+optional2
			        +"' AND "+condition_Name+"=3;";

		return sql;
		
	}

	@Override
	public ArrayList<BillPO> getBillList(UserPO user) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}


}
