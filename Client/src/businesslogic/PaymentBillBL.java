package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import blservice.billblservice.PaymentBillBLService;
import dataservice.PaymentBillDataService;
import ds_stub.PaymentBillDs_stub;
import po.billpo.PaymentBillPO;
import po.billpo.TransferItem;
import presentation.component.MyTableModel;
import vo.billvo.PaymentBillVO;

public class PaymentBillBL implements PaymentBillBLService {

	private PaymentBillDataService paymentBillDataService;
	
	private PaymentBillBL() {
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
            items.add(new TransferItem(row[0], price, row[2]));
        }

        return new PaymentBillPO(bill.getDate(), bill.getTime()
            , bill.getId(), bill.getOperator(), bill.getState()
            , bill.getCustomerId(), items);
    }

    private PaymentBillVO toVO(PaymentBillPO bill){
        String[] columnNames = {"银行账户", "转账金额", "备注"};
        ArrayList<TransferItem> items = bill.getTransferList();
        String[][] data = new String[columnNames.length][items.size()];
        for(int i = 0; i < data.length; i++){
            data[i] = toArray(items.get(i));
        }
        MyTableModel model = new MyTableModel(data, columnNames);
        PaymentBillVO paymentBillVO = new PaymentBillVO(bill.getDate(), bill.getTime(), bill.getId(), bill.getOperator()
                , bill.getState(), bill.getCustomerId());
        paymentBillVO.setTableModel(model);
        return paymentBillVO;
    }
    
    private String[] toArray(TransferItem item){
        // TODO the type and store to complete
        return new String[]{item.getAccountId(), Double.toString(item.getMoney()), item.getRemark()};
    }
}
