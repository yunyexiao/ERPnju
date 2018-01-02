package data;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import dataservice.PaymentBillDataService;
import po.billpo.PaymentBillPO;
import po.billpo.TransferItem;
public class PaymentBillData extends UnicastRemoteObject implements PaymentBillDataService{
	
	private String billName="PaymentBill";
	private String recordName="PaymentRecord";
	private String[] billAttributes={"PBID","PBCustomerID","PBOperatorID","PBSum","generateTime","PBCondition"};
	private String[] recordAttributes={"PRID","PRAccountID","PRTransfer","PRRemark"};
	
	public PaymentBillData() throws RemoteException {
		super();
	}

	@Override
	public boolean saveBill(PaymentBillPO bill) throws RemoteException {

		ArrayList<TransferItem> items=bill.getTransferList();
		boolean isExist=BillDataHelper.isBillExist(billName, billAttributes[0], bill);
		
		Object[] billValues={bill.getAllId(),bill.getCustomerId(),bill.getOperator(),bill.getSum(),
				bill.getDate()+" "+bill.getTime(),bill.getState()};

		try{
			if(!isExist){
				boolean b1 = SQLQueryHelper.add(billName, billValues);
				boolean b2 = true;
				for(int i=0;i<items.size();i++){
					b2 = b2 && SQLQueryHelper.add(recordName, bill.getAllId()
							,items.get(i).getAccountId(),items.get(i).getMoney(),items.get(i).getRemark());
				}
				return b1 && b2;
			}
			else{
				boolean b1=SQLQueryHelper.update(billName, billAttributes, billValues);
				boolean b2=false;
				for(int i=0;i<items.size();i++){
					Object[] recordValues={bill.getAllId(),items.get(i).getAccountId(),items.get(i).getMoney(),items.get(i).getRemark()};
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
		return BillDataHelper.getNewBillId(billName,billAttributes[0]);		
	}

	@Override
	public PaymentBillPO getBillById(String id) throws RemoteException {
		return BillDataHelper.getPaymentBill(id);
	}

}
