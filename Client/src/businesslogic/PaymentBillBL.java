package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import blservice.billblservice.BillExamineService;
import blservice.billblservice.BillOperationService;
import blservice.billblservice.PaymentBillBLService;
import dataservice.AccountDataService;
import dataservice.CustomerDataService;
import dataservice.PaymentBillDataService;
import ds_stub.AccountDs_stub;
import ds_stub.CustomerDs_stub;
import ds_stub.PaymentBillDs_stub;
import po.AccountPO;
import po.CustomerPO;
import po.billpo.BillPO;
import po.billpo.PaymentBillPO;
import po.billpo.TransferItem;
import presentation.tools.Timetools;
import rmi.Rmi;
import vo.billvo.BillVO;
import vo.billvo.PaymentBillVO;

public class PaymentBillBL implements PaymentBillBLService, BillOperationService, BillExamineService{

	private PaymentBillDataService paymentBillDataService;
	private AccountDataService accountDataService;
    private CustomerDataService customerDataService;
	
	public PaymentBillBL() {
		paymentBillDataService = Rmi.flag ? Rmi.getRemote(PaymentBillDataService.class) : new PaymentBillDs_stub();
		accountDataService = Rmi.flag ? Rmi.getRemote(AccountDataService.class) : new AccountDs_stub();
        customerDataService = Rmi.flag ? Rmi.getRemote(CustomerDataService.class) : new CustomerDs_stub();
	}
	
	@Override
	public String getNewId() {
        try {
            return "FKD-" + Timetools.getDate() + "-" + paymentBillDataService.getNewId();
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
            ArrayList<TransferItem> list = billPO.getTransferList();
            for (int i = 0; i < list.size(); i++) {
            	AccountPO accountPO = accountDataService.findById(list.get(i).getAccountId());
            	CustomerPO customerPO = customerDataService.findById(billPO.getCustomerId());
            	if (list.get(i).getMoney() <= accountPO.getMoney()) { //公司账户余额减少，客户应收减少
                	accountDataService.add(new AccountPO(accountPO.getId(), accountPO.getName(), accountPO.getMoney() - list.get(i).getMoney(), accountPO.getExistFlag()));
                	if ((customerPO.getReceivable() - billPO.getSum()) >= 0) {
                		customerDataService.add(new CustomerPO(customerPO.getId(), customerPO.getName(), customerPO.getTelNumber(),
                    			customerPO.getAddress(), customerPO.getMail(), customerPO.getCode(), customerPO.getSalesman(),
                    			customerPO.getRank(), customerPO.getType(), customerPO.getRecRange(), customerPO.getReceivable() - billPO.getSum(), 
                    			customerPO.getPayment(), customerPO.getExistFlag()));
                	}else {//如果账户多付了钱，就在客户的应付里要回来→_→ 
                		customerDataService.add(new CustomerPO(customerPO.getId(), customerPO.getName(), customerPO.getTelNumber(),
                    			customerPO.getAddress(), customerPO.getMail(), customerPO.getCode(), customerPO.getSalesman(),
                    			customerPO.getRank(), customerPO.getType(), customerPO.getRecRange(), 0, 
                    			customerPO.getPayment() + (billPO.getSum() - customerPO.getReceivable()), customerPO.getExistFlag()));
                	}
                	
            	}else {
                    billPO.setState(4);
                    billVO.setState(4);
                    saveBill(billVO);
                    return false;
            	}
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

	@Override
	public BillVO getBillById(String id) {
		try {
			return BillTools.toPaymentBillVO(paymentBillDataService.getBillById(id));
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}
}
