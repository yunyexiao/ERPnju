package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import blservice.billblservice.BillOperationService;
import blservice.billblservice.ReceiptBillBLService;
import dataservice.ReceiptBillDataService;
import ds_stub.ReceiptBillDs_stub;
import po.billpo.BillPO;
import po.billpo.ReceiptBillPO;
import po.billpo.TransferItem;
import vo.billvo.BillVO;
import vo.billvo.ReceiptBillVO;

public class ReceiptBillBL implements ReceiptBillBLService, BillOperationService{

	private ReceiptBillDataService receiptBillDataService;
	
	public ReceiptBillBL() {
		receiptBillDataService = new ReceiptBillDs_stub();//Rmi.getRemote(ReceiptBillDataService.class);
	}
	
	@Override
	public String getNewId() {
		 try {
	    		return receiptBillDataService.getNewId();
	        } catch (RemoteException e) {
	            e.printStackTrace();
	            return null;
	        }
	}

	@Override
	public boolean deleteBill(String id) {
		try{
			return receiptBillDataService.deleteBill(id);
		}catch(RemoteException e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean saveBill(ReceiptBillVO bill) {
		try{
            return receiptBillDataService.saveBill(toPO(bill));
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public boolean updateBill(ReceiptBillVO bill) {
		try{
            return receiptBillDataService.saveBill(toPO(bill));
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public ReceiptBillVO getBill(String id) {
		try{
            ReceiptBillPO bill = receiptBillDataService.getBillById(id);
            return BillTools.toReceiptBillVO(bill);
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
	}

	@Override
	public boolean offsetBill(String id){
	    try{
	        ReceiptBillPO bill = receiptBillDataService.getBillById(id);
	        ArrayList<TransferItem> items = new ArrayList<>();
	        bill.getTransferList().forEach(i -> items.add(new TransferItem(
	            i.getAccountId(), -i.getMoney(), i.getRemark()
	        )));
	        // TODO date time id not considered
	        return receiptBillDataService.saveBill(new ReceiptBillPO(
	            bill.getDate(), bill.getTime(), bill.getId(), bill.getOperator(), BillPO.PASS,
	            bill.getCustomerId(), items, -bill.getSum()
	        ));
	    }catch(RemoteException e){
	        e.printStackTrace();
	        return false;
	    }
	}
	
	@Override
	public boolean copyBill(BillVO bill){
	    if(bill instanceof ReceiptBillVO){
	        return saveBill((ReceiptBillVO) bill);
	    }
	    return false;
	}

	private ReceiptBillPO toPO(ReceiptBillVO bill){
        ArrayList<TransferItem> items = new ArrayList<>();
        double sum = 0;
        for(int i = 0; i < bill.getTableModel().getRowCount(); i++){
            String[] row = bill.getTableModel().getValueAtRow(i);
            double price = Double.parseDouble(row[1]);
            items.add(new TransferItem(row[0], price, row[2]));
            sum += price;
        }

        return new ReceiptBillPO(bill.getDate(), bill.getTime()
            , bill.getId(), bill.getOperator(), bill.getState()
            , bill.getCustomerId(), items, sum);
    }
}
