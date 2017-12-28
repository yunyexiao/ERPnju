package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JOptionPane;

import blservice.billblservice.BillExamineService;
import blservice.billblservice.BillOperationService;
import blservice.billblservice.CashCostBillBLService;
import dataservice.AccountDataService;
import dataservice.CashCostBillDataService;
import ds_stub.AccountDs_stub;
import ds_stub.CashCostBillDs_stub;
import po.AccountPO;
import po.billpo.BillPO;
import po.billpo.CashCostBillPO;
import po.billpo.CashCostItem;
import presentation.tools.Timetools;
import vo.billvo.BillVO;
import vo.billvo.CashCostBillVO;

public class CashCostBillBL implements CashCostBillBLService, BillOperationService, BillExamineService{

	private CashCostBillDataService cashCostBillDataService;
	private AccountDataService accountDataService;
	
	public CashCostBillBL() {
		//cashCostBillDataService = Rmi.flag ? Rmi.getRemote(CashCostBillDataService.class) : new CashCostBillDs_stub();
		//accountDataService = Rmi.flag ? Rmi.getRemote(AccountDataService.class) : new AccountDs_stub();
		
		cashCostBillDataService = new CashCostBillDs_stub();
		accountDataService = new AccountDs_stub();
	}
	
	@Override
	public String getNewId() {
        try {
        	Calendar c = Calendar.getInstance();
            String date = c.get(Calendar.YEAR) + ""
                        + c.get(Calendar.MONTH) + ""
                        + c.get(Calendar.DATE);
            return "XJFYD-" + date + "-" + cashCostBillDataService.getNewId();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
	}

	@Override
	public boolean deleteBill(String id) {
		try{
            CashCostBillPO bill = cashCostBillDataService.getBillById(id);
            if(bill.getState() == BillPO.PASS) return false;
            
            int length = id.length();
            return cashCostBillDataService.deleteBill(id.substring(length - 5, length));
		}catch(RemoteException e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean saveBill(CashCostBillVO bill) {
		try{
            return cashCostBillDataService.saveBill(toPO(bill));
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }	
	}

	@Override
    public boolean updateBill(CashCostBillVO bill) {
        try{
            return cashCostBillDataService.saveBill(toPO(bill));
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }
	
    public ArrayList<CashCostBillPO> getCashCostBillPOsByDate(String from, String to){
        try{
            return cashCostBillDataService.getBillsByDate(from, to);
        }catch(RemoteException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
	
	public boolean offsetBill(String id){
	    try{
            CashCostBillPO bill = cashCostBillDataService.getBillById(id);
            ArrayList<CashCostItem> items = new ArrayList<>();
            bill.getCashcostList().forEach(i -> items.add
                (new CashCostItem(i.getName(), -i.getMoney(), i.getRemark())));
            CashCostBillPO offset = new CashCostBillPO(
                Timetools.getDate(), Timetools.getTime(), this.getNewId(), bill.getOperator()
                , BillPO.PASS, bill.getAccountId(), items, -bill.getSum());
            return cashCostBillDataService.saveBill(offset);
	    }catch(RemoteException e){
	        e.printStackTrace();
	        return false;
	    }
	}
	
	@Override
	public boolean copyBill(BillVO bill){
	    if(bill instanceof CashCostBillVO){
	        CashCostBillVO oldOne = (CashCostBillVO) bill;
	        CashCostBillVO newOne = new CashCostBillVO(
	            Timetools.getDate(), Timetools.getTime(), this.getNewId(), 
	            oldOne.getOperator(), BillVO.PASS, oldOne.getAccountId(), oldOne.getTableModel()
	        );
	        return saveBill(newOne);
	    }
	    return false;
	}

    private CashCostBillPO toPO(CashCostBillVO bill){
        ArrayList<CashCostItem> items = new ArrayList<>();
        double sum = 0;
        for(int i = 0; i < bill.getTableModel().getRowCount(); i++){
            String[] row = bill.getTableModel().getValueAtRow(i);
            double price = Double.parseDouble(row[1]);
            items.add(new CashCostItem(row[0], price, row[2]));
            sum += price;
        }

        return new CashCostBillPO(bill.getDate(), bill.getTime()
            , bill.getId(), bill.getOperator(), bill.getState()
            , bill.getAccountId(), items, sum);
    }

	@Override
	public boolean examineBill(String billId) {
        try{
            CashCostBillPO billPO = cashCostBillDataService.getBillById(billId);
            CashCostBillVO billVO = BillTools.toCashCostBillVO(billPO);
            AccountPO accountPO = accountDataService.findById(billPO.getAccountId());
            if (accountPO.getMoney() >= billPO.getSum()) {
                billPO.setState(3);
                billVO.setState(3);
                accountDataService.add(new AccountPO(accountPO.getId(), accountPO.getName(),
                		accountPO.getMoney() - billPO.getSum(), accountPO.getExistFlag())); //针对改变后的账户余额，new一个accountPO传入数据库
                return saveBill(billVO);            	
            }else {
                billPO.setState(4);
                billVO.setState(4);
                saveBill(billVO);
            	return false;
            }
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public boolean notPassBill(String billId) {
        try{
            CashCostBillPO billPO = cashCostBillDataService.getBillById(billId);
            CashCostBillVO billVO = BillTools.toCashCostBillVO(billPO);
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
			return BillTools.toCashCostBillVO(cashCostBillDataService.getBillById(billId));
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}
}
