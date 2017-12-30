package data;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import dataservice.GiftBillDataService;
import po.billpo.GiftBillPO;
import po.billpo.GiftItem;
import po.billpo.SalesItemsPO;

public class GiftBillData extends UnicastRemoteObject implements GiftBillDataService {

	protected GiftBillData() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	private String billName="InventoryGiftBill";
	private String recordName="InventoryGiftRecord";
	private String[] billAttributes={"IGBID","IGBOperatorID","IGBCondition","generateTime","IGBSalesBillID","IGBCustomerID"};
	private String[] recordAttributes={"IGRID","IGRComID","IGRComQuantity","IGRComPrice"};


	@Override
	public boolean add(GiftBillPO bill) throws RemoteException {
		ArrayList<GiftItem> items = bill.getGifts();
		boolean isExist=BillDataHelper.isBillExist(billName, billAttributes[0], bill);
		Object[] billValues={bill.getAllId(), bill.getOperator(), bill.getState(),
				bill.getDate() + " "+bill.getTime(), bill.getSalesBillId(), bill.getCustomerId()};
		try{
			if(!isExist){
				boolean b1 = SQLQueryHelper.add(billName, billValues);
				boolean b2 = true;
				for(int i=0;i<items.size();i++){
					b2=b2&&SQLQueryHelper.add(recordName, bill.getAllId(), items.get(i).getComId(),
							items.get(i).getNum(), items.get(i).getPrice());
				}
				return b1&&b2;
			}
			else{
				boolean b1=SQLQueryHelper.update(billName, billAttributes, billValues);
				boolean b2=false;
				for(int i=0;i<items.size();i++){
					Object[] recordValues={bill.getAllId(), items.get(i).getComId(),
							items.get(i).getNum(), items.get(i).getPrice()};
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
	public String getNewId() throws RemoteException {
		// TODO Auto-generated method stub
		return BillDataHelper.getNewBillId(billName, billAttributes[0]);
	}

	@Override
	public GiftBillPO getById(String id) throws RemoteException {
		return BillDataHelper.getGiftBill(id);
	}

}
