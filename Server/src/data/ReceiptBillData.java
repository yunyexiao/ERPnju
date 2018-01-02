package data;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import dataservice.ReceiptBillDataService;
import po.billpo.ReceiptBillPO;
import po.billpo.TransferItem;
public class ReceiptBillData extends UnicastRemoteObject implements ReceiptBillDataService{
	
	private String billName="ReceiptBill";
	private String recordName="ReceiptRecord";
	private String[] billAttributes={"RBID","RBCustomerID","RBOperatorID","RBSum","generateTime","RBCondition"};
	private String[] recordAttributes={"RRID","RRAccountID","RRTransfer","RRRemark"};

	public ReceiptBillData() throws RemoteException {
		super();
	}

	@Override
	public boolean saveBill(ReceiptBillPO bill) throws RemoteException {

		ArrayList<TransferItem> items=bill.getTransferList();
		boolean isExist=BillDataHelper.isBillExist(billName, billAttributes[0], bill);
		Object[] billValues={bill.getAllId(), bill.getCustomerId(), bill.getOperator(), bill.getSum(),
				bill.getDate() + " "+bill.getTime(), bill.getState()};

		try{
			if(!isExist){
			boolean b1 = SQLQueryHelper.add(billName, billValues);
			boolean b2 = true;
			for(int i = 0; i < items.size(); i++) {
				b2 = b2 && SQLQueryHelper.add(recordName, bill.getAllId()
						,items.get(i).getAccountId(),items.get(i).getMoney(),items.get(i).getRemark());
			}
			return b1 && b2;
			}
			else{
				boolean b1=SQLQueryHelper.update(billName, billAttributes, billValues);
				boolean b2=false;
				for(int i=0;i<items.size();i++){
					Object[] recordValues={bill.getAllId(), items.get(i).getAccountId(), items.get(i).getMoney(),
							items.get(i).getRemark()};
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
	public ReceiptBillPO getBillById(String id) throws RemoteException {
		return BillDataHelper.getReceiptBill(id);
	}

}
