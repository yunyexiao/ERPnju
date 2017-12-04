package businesslogic.inter;

import java.rmi.RemoteException;
import java.util.ArrayList;

import blservice.billblservice.PaymentBillBLService;
import dataservice.PaymentBillDataService;
import dataservice.ReceiptBillDataService;
import ds_stub.PaymentBillDs_stub;
import ds_stub.ReceiptBillDs_stub;
import po.billpo.PaymentBillPO;
import po.billpo.ReceiptBillPO;
import po.billpo.TransferItem;
import vo.billvo.PaymentBillVO;
import vo.billvo.ReceiptBillVO;

public class PaymentBillBL implements PaymentBillBLService {

	private PaymentBillDataService paymentBillDs;
    
    public PaymentBillBL() {
    	paymentBillDs = new PaymentBillDs_stub();
    } 
	
	@Override
	public String getNewId() {
		try {
            return paymentBillDs.getNewId();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
	}

	@Override
	public boolean deleteBill(String id) {
		try{
            return paymentBillDs.deleteBill(id);
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public boolean saveBill(PaymentBillVO bill) {
		try{
            return paymentBillDs.saveBill(toPO(bill));
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public boolean updateBill(PaymentBillVO bill) {
		try{
            return paymentBillDs.saveBill(toPO(bill));
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public PaymentBillVO getBill(String id) {
		try{
		    PaymentBillPO bill = paymentBillDs.getBillById(id);
            return toVO(bill);
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
	}
		
	private PaymentBillPO toPO(PaymentBillVO bill){
        ArrayList<TransferItem> items = new ArrayList<>();
        for(int i = 0; i < bill.getTableModel().getRowCount(); i++){
            String[] row = bill.getTableModel().getValueAtRow(i);
            double price = Double.parseDouble(row[1]);
            items.add(new TransferItem(row[0], price, row[2]));//我tm也不知道到底数组下标填几
        }

        return new PaymentBillPO(bill.getDate(), bill.getTime()
            , bill.getId(), bill.getOperator(), bill.getState(), bill.getCustomerId(),  items);
    }

    private PaymentBillVO toVO(PaymentBillPO bill){
        return new PaymentBillVO(bill.getDate(), bill.getTime(), bill.getId(), bill.getOperator()
            , bill.getState(), bill.getCustomerId());
    }
}
