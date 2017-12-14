package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import blservice.billblservice.BillOperationService;
import blservice.billblservice.PaymentBillBLService;
import dataservice.PaymentBillDataService;
import ds_stub.PaymentBillDs_stub;
import po.billpo.BillPO;
import po.billpo.PaymentBillPO;
import po.billpo.TransferItem;
import vo.billvo.BillVO;
import vo.billvo.PaymentBillVO;

public class PaymentBillBL implements PaymentBillBLService, BillOperationService{

	private PaymentBillDataService paymentBillDataService;
	
	public PaymentBillBL() {
		paymentBillDataService = new PaymentBillDs_stub();//Rmi.getRemote(ReceiptBillDataService.class);
	}
	
	@Override
	public String getNewId() {
		 try {
	    		return paymentBillDataService.getNewId();
	        } catch (RemoteException e) {
	            e.printStackTrace();
	            return null;
	        }
	}

	@Override
	public boolean deleteBill(String id) {
		try{
			return paymentBillDataService.deleteBill(id);
		}catch(RemoteException e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean saveBill(PaymentBillVO bill) {
		try{
            return paymentBillDataService.saveBill(toPO(bill));
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public boolean updateBill(PaymentBillVO bill) {
		try{
            return paymentBillDataService.saveBill(toPO(bill));
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public PaymentBillVO getBill(String id) {
		try{
            PaymentBillPO bill = paymentBillDataService.getBillById(id);
            return BillTools.toPaymentBillVO(bill);
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
	}

	@Override
	public boolean offsetBill(String id){
	    try{
	        PaymentBillPO bill = paymentBillDataService.getBillById(id);
	        ArrayList<TransferItem> items = new ArrayList<>();
	        bill.getTransferList().forEach(i -> items.add(
	            new TransferItem(i.getAccountId(), -i.getMoney(), i.getRemark())));
	        PaymentBillPO offset = new PaymentBillPO(
	            bill.getDate(), bill.getTime(), bill.getId(), bill.getOperator()
	            , BillPO.PASS, bill.getCustomerId(), items);
	        return paymentBillDataService.saveBill(offset);
	    }catch(RemoteException e){
	        e.printStackTrace();
	        return false;
	    }
	}
	
	@Override
	public boolean copyBill(BillVO bill){
	    if(bill instanceof PaymentBillVO){
	        return saveBill((PaymentBillVO) bill);
	    }
	    return false;
	}

	private PaymentBillPO toPO(PaymentBillVO bill){
        ArrayList<TransferItem> items = new ArrayList<>();
        for(int i = 0; i < bill.getTableModel().getRowCount(); i++){
            String[] row = bill.getTableModel().getValueAtRow(i);
            double price = Double.parseDouble(row[1]);
            items.add(new TransferItem(row[0], price, row[2]));
        }

        return new PaymentBillPO(bill.getDate(), bill.getTime()
            , bill.getId(), bill.getOperator(), bill.getState()
            , bill.getCustomerId(), items);
    }
}
