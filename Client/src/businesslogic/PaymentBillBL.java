package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;

import blservice.billblservice.BillExamineService;
import blservice.billblservice.BillOperationService;
import blservice.billblservice.PaymentBillBLService;
import dataservice.PaymentBillDataService;
import ds_stub.PaymentBillDs_stub;
import po.billpo.BillPO;
import po.billpo.PaymentBillPO;
import po.billpo.TransferItem;
import presentation.tools.Timetools;
import rmi.Rmi;
import vo.billvo.BillVO;
import vo.billvo.PaymentBillVO;

public class PaymentBillBL implements PaymentBillBLService, BillOperationService, BillExamineService{

	private PaymentBillDataService paymentBillDataService;
	
	public PaymentBillBL() {
		paymentBillDataService = Rmi.flag ? Rmi.getRemote(PaymentBillDataService.class) : new PaymentBillDs_stub();
	}
	
	@Override
	public String getNewId() {
        try {
        	Calendar c = Calendar.getInstance();
            String date = c.get(Calendar.YEAR) + ""
                        + c.get(Calendar.MONTH) + ""
                        + c.get(Calendar.DATE);
            return "FKD-" + date + "-" + paymentBillDataService.getNewId();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
	}

	@Override
	public boolean deleteBill(String id) {
		try{
            PaymentBillPO bill = paymentBillDataService.getBillById(id);
            if(bill.getState() == BillPO.PASS) return false;
            
            int length = id.length();
            return paymentBillDataService.deleteBill(id.substring(length - 5, length));
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
	public boolean offsetBill(String id){
	    try{
	        PaymentBillPO bill = paymentBillDataService.getBillById(id);
	        ArrayList<TransferItem> items = new ArrayList<>();
	        bill.getTransferList().forEach(i -> items.add(
	            new TransferItem(i.getAccountId(), -i.getMoney(), i.getRemark())));
	        PaymentBillPO offset = new PaymentBillPO(
	            Timetools.getDate(), Timetools.getTime(), this.getNewId(), bill.getOperator()
	            , BillPO.PASS, bill.getCustomerId(), items, -bill.getSum());
	        return paymentBillDataService.saveBill(offset);
	    }catch(RemoteException e){
	        e.printStackTrace();
	        return false;
	    }
	}
	
	@Override
	public boolean copyBill(BillVO bill){
	    if(bill instanceof PaymentBillVO){
	        PaymentBillVO old = (PaymentBillVO) bill;
	        PaymentBillVO copy = new PaymentBillVO(
	            Timetools.getDate(), Timetools.getTime(), this.getNewId(), 
	            old.getOperator(), BillVO.PASS, old.getCustomerId()
	        );
	        copy.setTableModel(old.getTableModel());
	        return saveBill(copy);
	    }
	    return false;
	}

	private PaymentBillPO toPO(PaymentBillVO bill){
        ArrayList<TransferItem> items = new ArrayList<>();
        double sum = 0;
        for(int i = 0; i < bill.getTableModel().getRowCount(); i++){
            String[] row = bill.getTableModel().getValueAtRow(i);
            double price = Double.parseDouble(row[1]);
            items.add(new TransferItem(row[0], price, row[2]));
            sum += price;
        }

        return new PaymentBillPO(bill.getDate(), bill.getTime()
            , bill.getId(), bill.getOperator(), bill.getState()
            , bill.getCustomerId(), items, sum);
    }

	@Override
	public boolean examineBill(String billId) {
        try{
            PaymentBillPO billPO = paymentBillDataService.getBillById(billId);
            PaymentBillVO billVO = BillTools.toPaymentBillVO(billPO);
            billPO.setState(3);
            billVO.setState(3);
            return saveBill(billVO);
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public boolean notPassBill(String billId) {
        try{
            PaymentBillPO billPO = paymentBillDataService.getBillById(billId);
            PaymentBillVO billVO = BillTools.toPaymentBillVO(billPO);
            billPO.setState(4);
            billVO.setState(4);
            return saveBill(billVO);
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
	}
}
