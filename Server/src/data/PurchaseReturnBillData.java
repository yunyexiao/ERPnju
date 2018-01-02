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

	private String billName="PurchaseReturnBill";
	private String recordName="PurchaseReturnRecord";
	private String[] billAttributes={"PRBID","PRBSupplierID","PRBOperatorID","PRBSum","PRBRemark","PRBCondition","generateTime"};
	private String[] recordAttributes={"PRRID","PRRComID","PRRComQuantity","PRRComSum","PRRRemark","PRRComPrice"};

	@Override
	public boolean saveBill(PurchaseReturnBillPO bill) throws RemoteException {
        ArrayList<SalesItemsPO> items = bill.getPurchaseReturnBillItems();
		boolean isExist=BillDataHelper.isBillExist(billName, billAttributes[0], bill);
		Object[] billValues={ bill.getAllId(), bill.getSupplierId(), bill.getOperator(), bill.getSum(),
				bill.getRemark(), bill.getState(), bill.getDate() + " "+bill.getTime()};
		
		try{
			if(!isExist){
			boolean b1 = SQLQueryHelper.add(billName, bill.getAllId()
					,bill.getSupplierId(),bill.getOperator(),bill.getSum(),bill.getRemark()
					,bill.getState(),bill.getDate() + " "+bill.getTime());
			boolean b2 = true;
			for(int i=0;i<items.size();i++){
				b2 = b2 && SQLQueryHelper.add(recordName, bill.getAllId()
						,items.get(i).getComId(),items.get(i).getComQuantity(),items.get(i).getComSum()
						,items.get(i).getComRemark(),items.get(i).getComPrice());
			}
			return b1 && b2;
			}
			else{
				boolean b1=SQLQueryHelper.update(billName, billAttributes, billValues);
				boolean b2=false;
				for(int i=0;i<items.size();i++){
					Object[] recordValues={ bill.getAllId(), items.get(i).getComId(), items.get(i).getComQuantity(),
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
	public boolean deleteBill(String id) throws RemoteException {
		return BillDataHelper.deleteBill(id);
	}

	@Override
	public String getNewId() throws RemoteException {
		return BillDataHelper.getNewBillId(billName,billAttributes[0]);
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
			ResultSet r=s.executeQuery("SELECT * FROM "+billName+
					" WHERE generateTime>'"+from+"' AND generateTime<DATEADD(DAY,1,"+"'"+to+"');");
			while(r.next()) bills.add(BillDataHelper.getPurchaseRetrunBill(r.getString(billAttributes[0])));
			return bills;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

}
