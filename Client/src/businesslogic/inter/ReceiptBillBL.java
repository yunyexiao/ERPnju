package businesslogic.inter;

import java.rmi.RemoteException;
import java.util.ArrayList;

import blservice.billblservice.ReceiptBillBLService;
import dataservice.ReceiptBillDataService;
import ds_stub.ReceiptBillDs_stub;
import po.billpo.ReceiptBillPO;
import po.billpo.TransferItem;
import presentation.component.MyTableModel;
import vo.billvo.ReceiptBillVO;

public class ReceiptBillBL implements ReceiptBillBLService {

    private ReceiptBillDataService receiptBillDs;
    
    public ReceiptBillBL() {
    	receiptBillDs = new ReceiptBillDs_stub();
    } 
	
	@Override
	public String getNewId() {
		try {
            return receiptBillDs.getNewId();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
	}

	@Override
	public boolean deleteBill(String id) {
		try{
            return receiptBillDs.deleteBill(id);
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public boolean saveBill(ReceiptBillVO bill) {
		try{
            return receiptBillDs.saveBill(toPO(bill));
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public boolean updateBill(ReceiptBillVO bill) {
		try{
            return receiptBillDs.saveBill(toPO(bill));
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public ReceiptBillVO getBill(String id) {
		  try{
			    ReceiptBillPO bill = receiptBillDs.getBillById(id);
	            return toVO(bill);
	        }catch(RemoteException e){
	            e.printStackTrace();
	            return null;
	        }
	}

    private ReceiptBillPO toPO(ReceiptBillVO bill){
        ArrayList<TransferItem> items = new ArrayList<>();
        for(int i = 0; i < bill.getTableModel().getRowCount(); i++){
            String[] row = bill.getTableModel().getValueAtRow(i);
            double price = Double.parseDouble(row[1]);
            items.add(new TransferItem(row[0], price, row[2]));//我tm也不知道到底数组下标填几
        }

        return new ReceiptBillPO(bill.getDate(), bill.getTime()
            , bill.getId(), bill.getOperator(), bill.getState(), bill.getCustomerId(),  items);
    }

    private ReceiptBillVO toVO(ReceiptBillPO bill){
    	String[] columnNames = {"", "", ""};
        ArrayList<TransferItem> items = bill.getTransferList();
        String[][] data = new String[columnNames.length][items.size()];
        for(int i = 0; i < data.length; i++){
            data[i] = toArray(items.get(i));
        }
        MyTableModel model = new MyTableModel(data, columnNames);
        return new ReceiptBillVO(bill.getDate(), bill.getTime(), bill.getId(), bill.getOperator()
            , bill.getState(), bill.getCustomerId(), model);
    }

    private String[] toArray(TransferItem item){
        // TODO the type and store to complete
        return new String[]{item.getAccountId(), Double.toString(item.getMoney()), item.getRemark()};
    }

}
