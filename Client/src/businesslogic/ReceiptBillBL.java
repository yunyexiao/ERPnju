package businesslogic;

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

	private ReceiptBillDataService receiptBillDataService;
	
	private ReceiptBillBL() {
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
            items.add(new TransferItem(row[0], price, row[2]));
        }

        return new ReceiptBillPO(bill.getDate(), bill.getTime()
            , bill.getId(), bill.getOperator(), bill.getState()
            , bill.getCustomerId(), items);
    }

    private ReceiptBillVO toVO(ReceiptBillPO bill){
        String[] columnNames = {"银行账户", "转账金额", "备注"};
        ArrayList<TransferItem> items = bill.getTransferList();
        String[][] data = new String[columnNames.length][items.size()];
        for(int i = 0; i < data.length; i++){
            data[i] = toArray(items.get(i));
        }
        MyTableModel model = new MyTableModel(data, columnNames);
        ReceiptBillVO receiptBillVO = new ReceiptBillVO(bill.getDate(), bill.getTime(), bill.getId(), bill.getOperator()
                , bill.getState(), bill.getCustomerId(), model);
        return receiptBillVO;
    }
    
    private String[] toArray(TransferItem item){
        // TODO the type and store to complete
        return new String[]{item.getAccountId(), Double.toString(item.getMoney()), item.getRemark()};
    }
}
