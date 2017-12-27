package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import po.billpo.CashCostBillPO;
import po.billpo.CashCostItem;
import po.billpo.PaymentBillPO;
import po.billpo.PurchaseBillPO;
import po.billpo.ReceiptBillPO;
import po.billpo.SalesItemsPO;
import po.billpo.TransferItem;

public class BillDataHelper {
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

	public static CashCostBillPO getCashCostBill(String id) {
		try {
			ArrayList<CashCostItem> items = new ArrayList<CashCostItem>();
			ResultSet r1 = SQLQueryHelper.getRecordByAttribute("CashCostRecord", "CCRID", id);
			while(r1.next()) {	
				CashCostItem item=new CashCostItem(r1.getString("CCRCostName"),r1.getDouble("CCRMoney"),r1.getString("CCRRemark"));
			    items.add(item);
			}
			ResultSet r2=SQLQueryHelper.getRecordByAttribute("CashCostBill", "CCBID", id);
			r2.next();
			return new CashCostBillPO(
					dateFormat.format(r2.getDate("generateTime")),
					timeFormat.format(r2.getTime("generateTime")),
					r2.getString("CCBID").split("-")[2],
					r2.getString("CCBOperatorID"),
					r2.getInt("CCBCondition"),
					r2.getString("CCBAccountID"),
					items,
					r2.getDouble("CCBSum"));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static ReceiptBillPO getReceiptBill(String id) {
		try {
			ResultSet r2 = SQLQueryHelper.getRecordByAttribute("ReceiptRecord", "RRID", id);
			ArrayList<TransferItem> items=new ArrayList<TransferItem>();
			while(r2.next()){
				TransferItem item=new TransferItem(
						r2.getString("RRAccountID"),
						r2.getDouble("RRTransfer"),
						r2.getString("RRRemark"));
				items.add(item);
			}
			ResultSet r = SQLQueryHelper.getRecordByAttribute("ReceiptBill", "RBID", id);
			r.next();
			ReceiptBillPO bill=new ReceiptBillPO(
					dateFormat.format(r.getDate("generateTime")),
					timeFormat.format(r.getTime("generateTime")),
					r.getString("RBID").split("-")[2],
					r.getString("RBOperatorID"),
					r.getInt("RBCondition"),
					r.getString("RBCustomerID"),
					items,
					r.getDouble("RBSum"));
			return bill;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static PaymentBillPO getPaymentBill(String id) {
		try{
			ArrayList<TransferItem> items = new ArrayList<TransferItem>();
			ResultSet r2 = SQLQueryHelper.getRecordByAttribute("PaymentRecord", "PRID", id);
			while(r2.next()){	
				TransferItem item = new TransferItem(r2.getString("PRAccountID"),r2.getDouble("PRTransfer"),r2.getString("PRRemark"));
			    items.add(item);
			}
			ResultSet r = SQLQueryHelper.getRecordByAttribute("PaymentBill", "PBID", id);
			r.next();
			PaymentBillPO bill = new PaymentBillPO(
					dateFormat.format(r.getDate("generateTime")),
					timeFormat.format(r.getTime("generateTime")),
					r.getString("PBID").split("-")[2],
					r.getString("PBOperatorID"),
					r.getInt("PBCondition"),
					r.getString("PBCustomerID"),
					items,
					r.getDouble("PBSum"));
			return bill;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static PurchaseBillPO getPurchaseBill(String id){
		try{
			ArrayList<SalesItemsPO> items = new ArrayList<SalesItemsPO>();
			ResultSet r1=SQLQueryHelper.getRecordByAttribute("PurchaseRecord", "PRID", id);
			while(r1.next()){	
			    SalesItemsPO item=new SalesItemsPO(
					r1.getString("PRComID"),
					r1.getString("PRRemark"),
					r1.getInt("PRComQuantity"),
					r1.getDouble("PRComPrice"),
					r1.getDouble("PRComSum"));
			    items.add(item);
			}
			ResultSet r2 = SQLQueryHelper.getRecordByAttribute("PurchaseBill", "PBID", id);
			r2.next();
			return new PurchaseBillPO(
				dateFormat.format(r2.getDate("generateTime")),
				timeFormat.format(r2.getTime("generateTime")),
				r2.getString("PBID").split("-")[2],
				r2.getString("PBOperatorID"),
				r2.getInt("PBCondition"),
				r2.getString("PBSupplierID"),
				r2.getString("PBRemark"),
				r2.getDouble("PBSum"),
				items);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	//获取单据类id,不加标识
	public static String getNewBillId(String tableName,String idName){
		String newId=null;
		int num=0;
		
		Calendar now = Calendar.getInstance();
		int year=now.get(Calendar.YEAR), month=now.get(Calendar.MONTH),  day=now.get(Calendar.DAY_OF_MONTH);
		String date=year+"-"+(month+1)+"-"+day;
		
		try{
			Statement s=DataHelper.getInstance().createStatement();
			ResultSet r=s.executeQuery("SELECT "+idName+" FROM "+tableName+" WHERE generateTime>"
					+"'"+date+"' "+"AND generateTime<DATEADD(DAY,1,"+"'"+date+"');");
			while(r.next()){
				num++;
			}
			num++;
			
			newId=String.format("%05d", num);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return newId;	
	}
	
	public static boolean deleteBill(String id) {
		String[][] data = {{"XJFYD","FKD","SKD","JHD","JHTHD","XSD","XSTHD","BYD","BSD"},
				{"CashCostBill","PaymentBill","ReceiptBill","PurchaseBill"},
				{"CCBID","PBID","RBID","PBID"}};
		String type = id.split("-")[0];
		int num = 0;
		for (int i = 0; i < data[0].length; i++) if (type.equals(data[0][i])) num = i;
		String billTableName = data[1][num];
		String billTableId = data[2][num];
		try {
			Statement s1 = DataHelper.getInstance().createStatement();
			int r1 = s1.executeUpdate("DELETE FROM "+billTableName+" WHERE "+billTableId+"='"+id+"';");
			return r1 > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
