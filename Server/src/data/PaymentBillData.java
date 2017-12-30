package data;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import dataservice.PaymentBillDataService;
import po.billpo.PaymentBillPO;
import po.billpo.TransferItem;
public class PaymentBillData extends UnicastRemoteObject implements PaymentBillDataService{
	
	private String billTableName="PaymentBill";
	private String recordTableName="PaymentRecord";
	private String billIdName="PBID";
	private String recordIdName="PRID";

	public PaymentBillData() throws RemoteException {
		super();
	}

	@Override
	public boolean saveBill(PaymentBillPO bill) throws RemoteException {

		ArrayList<TransferItem> items=bill.getTransferList();
		try{
			boolean b1 = SQLQueryHelper.add(billTableName, bill.getAllId()
					,bill.getCustomerId(),bill.getOperator(),bill.getSum()
					,bill.getDate() + " "+bill.getTime(),bill.getState());
			boolean b2 = true;
			for(int i=0;i<items.size();i++){
				b2 = b2 && SQLQueryHelper.add(recordTableName, bill.getAllId()
						,items.get(i).getAccountId(),items.get(i).getMoney(),items.get(i).getRemark());
			}
			if(b1 && b2) return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return false;
	}

	@Override
	public boolean deleteBill(String billid) throws RemoteException {
		return BillDataHelper.deleteBill(billid);
	}

	@Override
	public String getNewId() throws RemoteException {
		return BillDataHelper.getNewBillId(billTableName,billIdName);		
	}

	@Override
	public PaymentBillPO getBillById(String id) throws RemoteException {
		return BillDataHelper.getPaymentBill(id);
	}

}
