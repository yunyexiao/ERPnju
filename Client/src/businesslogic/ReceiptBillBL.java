package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import blservice.billblservice.BillExamineService;
import blservice.billblservice.BillOperationService;
import blservice.billblservice.ReceiptBillBLService;
import dataservice.AccountDataService;
import dataservice.CustomerDataService;
import dataservice.ReceiptBillDataService;
import ds_stub.AccountDs_stub;
import ds_stub.CustomerDs_stub;
import ds_stub.ReceiptBillDs_stub;
import po.AccountPO;
import po.CustomerPO;
import po.billpo.BillPO;
import po.billpo.PaymentBillPO;
import po.billpo.ReceiptBillPO;
import po.billpo.TransferItem;
import presentation.tools.Timetools;
import rmi.Rmi;
import vo.billvo.BillVO;
import vo.billvo.PaymentBillVO;
import vo.billvo.ReceiptBillVO;

public class ReceiptBillBL implements ReceiptBillBLService, BillOperationService, BillExamineService{

	private ReceiptBillDataService receiptBillDataService;
	private AccountDataService accountDataService;
    private CustomerDataService customerDataService;
	
	public ReceiptBillBL() {
		receiptBillDataService = Rmi.flag ? Rmi.getRemote(ReceiptBillDataService.class) : new ReceiptBillDs_stub();
		accountDataService = Rmi.flag ? Rmi.getRemote(AccountDataService.class) : new AccountDs_stub();		
        customerDataService = Rmi.flag ? Rmi.getRemote(CustomerDataService.class) : new CustomerDs_stub();
	}
	
	@Override
	public String getNewId() {
		 try {
	    		return "SKD-"+Timetools.getDate()+"-"+receiptBillDataService.getNewId();
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
	public boolean offsetBill(String id){
	    try{
	        ReceiptBillPO bill = receiptBillDataService.getBillById(id);
	        ArrayList<TransferItem> items = new ArrayList<>();
	        bill.getTransferList().forEach(i -> items.add(new TransferItem(
	            i.getAccountId(), -i.getMoney(), i.getRemark()
	        )));
	        return receiptBillDataService.saveBill(new ReceiptBillPO(
	            Timetools.getDate(), Timetools.getTime(), this.getNewId(), bill.getOperator(), BillPO.PASS,
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
	        ReceiptBillVO old = (ReceiptBillVO) bill;
	        ReceiptBillVO copy = new ReceiptBillVO(
	            Timetools.getDate(), Timetools.getTime(), this.getNewId(), old.getOperator(),
	            BillVO.PASS, old.getCustomerId(), old.getTableModel()
	        );
	        return saveBill(copy);
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

	@Override
	public boolean examineBill(String billId) {
        try{
            ReceiptBillPO billPO = receiptBillDataService.getBillById(billId);
            ReceiptBillVO billVO = BillTools.toReceiptBillVO(billPO);
            ArrayList<TransferItem> list = billPO.getTransferList();
            CustomerPO customerPO = customerDataService.findById(billPO.getCustomerId());
            for (int i = 0; i < list.size(); i++) {
            	if ((customerPO.getPayment() - billPO.getSum()) >= 0) {//如果应付>=收款金额，则相应的更改应付；否则则将应付清零，在应收中替客户将多付的钱要回来
            		customerDataService.add(new CustomerPO(customerPO.getId(), customerPO.getName(), customerPO.getTelNumber(),
                			customerPO.getAddress(), customerPO.getMail(), customerPO.getCode(), customerPO.getSalesman(),
                			customerPO.getRank(), customerPO.getType(), customerPO.getRecRange(), customerPO.getReceivable(), 
                			customerPO.getPayment() - billPO.getSum(), customerPO.getExistFlag()));
            	}else {
            		customerDataService.add(new CustomerPO(customerPO.getId(), customerPO.getName(), customerPO.getTelNumber(),
                			customerPO.getAddress(), customerPO.getMail(), customerPO.getCode(), customerPO.getSalesman(),
                			customerPO.getRank(), customerPO.getType(), customerPO.getRecRange(), customerPO.getReceivable() + (billPO.getSum() - customerPO.getPayment()), 
                			0, customerPO.getExistFlag()));
            	}
            	AccountPO accountPO = accountDataService.findById(list.get(i).getAccountId());
            	accountDataService.add(new AccountPO(accountPO.getId(), accountPO.getName(), accountPO.getMoney() + list.get(i).getMoney(), accountPO.getExistFlag()));
            }
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
            ReceiptBillPO billPO = receiptBillDataService.getBillById(billId);
            ReceiptBillVO billVO = BillTools.toReceiptBillVO(billPO);
            billPO.setState(4);
            billVO.setState(4);
            return saveBill(billVO);
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public BillVO getBillById(String billId) {
		try {
			return BillTools.toReceiptBillVO(receiptBillDataService.getBillById(billId));
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}
}
