package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import blservice.billblservice.BillExamineService;
import blservice.billblservice.BillOperationService;
import blservice.billblservice.ReceiptBillBLService;
import businesslogic.inter.AddLogInterface;
import dataservice.AccountDataService;
import dataservice.CustomerDataService;
import dataservice.ReceiptBillDataService;
import ds_stub.AccountDs_stub;
import ds_stub.CustomerDs_stub;
import ds_stub.ReceiptBillDs_stub;
import po.AccountPO;
import po.CustomerPO;
import po.billpo.BillPO;
import po.billpo.ReceiptBillPO;
import po.billpo.TransferItem;
import presentation.tools.Timetools;
import rmi.Rmi;
import vo.billvo.BillVO;
import vo.billvo.ReceiptBillVO;

public class ReceiptBillBL implements ReceiptBillBLService, BillOperationService, BillExamineService{

	private ReceiptBillDataService receiptBillDataService = Rmi.flag ? Rmi.getRemote(ReceiptBillDataService.class) : new ReceiptBillDs_stub();
	private AddLogInterface addLog = new LogBL();
	private AccountDataService accountDataService = Rmi.flag ? Rmi.getRemote(AccountDataService.class) : new AccountDs_stub();
    private CustomerDataService customerDataService = Rmi.flag ? Rmi.getRemote(CustomerDataService.class) : new CustomerDs_stub();
	
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
			if (receiptBillDataService.deleteBill(id)) {
            	addLog.add("删除收款单", "删除的收款单单据编号为"+id);
            	return true;
            } else return false;
		}catch(RemoteException e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean saveBill(ReceiptBillVO bill) {
		return saveBill(bill, "保存收款单", "保存的收款单单据编号为"+bill.getAllId());
	}
	
	private boolean saveBill(ReceiptBillVO bill, String operation, String detail) {
		try{
            if (receiptBillDataService.saveBill(toPO(bill))) {
            	addLog.add(operation, detail);
            	return true;
            } else return false;
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
	        ReceiptBillPO offset = new ReceiptBillPO(
	            Timetools.getDate(), Timetools.getTime(), this.getNewId(), bill.getOperator(), BillPO.PASS,
	            bill.getCustomerId(), items, -bill.getSum());
	        if (receiptBillDataService.saveBill(offset)) {
            	addLog.add("红冲收款单", "被红冲的收款单单据编号为"+bill.getAllId());
            	return true;
            } else return false;
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
	        return saveBill(copy, "红冲并复制收款单", "红冲并复制后新的收款单单据编号为"+copy.getAllId());
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
            return saveBill(billVO, "审核收款单", "通过审核的收款单单据编号为"+billId);
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
            return saveBill(billVO, "审核收款单", "单据编号为"+billId+"的收款单审核未通过");
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
