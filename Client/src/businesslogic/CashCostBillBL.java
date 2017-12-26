package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import blservice.billblservice.BillExamineService;
import blservice.billblservice.BillOperationService;
import blservice.billblservice.CashCostBillBLService;
import businesslogic.inter.AddLogInterface;
import dataservice.CashCostBillDataService;
import ds_stub.CashCostBillDs_stub;
import po.billpo.BillPO;
import po.billpo.CashCostBillPO;
import po.billpo.CashCostItem;
import presentation.tools.Timetools;
import rmi.Rmi;
import vo.billvo.BillVO;
import vo.billvo.CashCostBillVO;

public class CashCostBillBL implements CashCostBillBLService, BillOperationService, BillExamineService{

	private CashCostBillDataService cashCostBillDataService = Rmi.flag ? Rmi.getRemote(CashCostBillDataService.class) : new CashCostBillDs_stub();
	private AddLogInterface addLog = new LogBL();
	
	@Override
	public String getNewId() {
        try {
            return "XJFYD-" + Timetools.getDate() + "-" + cashCostBillDataService.getNewId();
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
            if (cashCostBillDataService.deleteBill(id.substring(length - 5, length))) {
            	addLog.add("ɾ���ֽ���õ�", "ɾ�����ֽ���õ����ݱ��Ϊ"+id);
            	return true;
            } else return false;
		}catch(RemoteException e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean saveBill(CashCostBillVO bill) {
		return saveBill(bill, "�����ֽ���õ�", "������ֽ���õ����ݱ��Ϊ"+bill.getAllId());
	}
	
	private boolean saveBill(CashCostBillVO bill, String operation, String detail) {
		try{
            if (cashCostBillDataService.saveBill(toPO(bill))) {
            	addLog.add(operation, detail);
            	return true;
            } else return false;
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
            if (cashCostBillDataService.saveBill(offset)) {
            	addLog.add("����ֽ���õ�", "�������ֽ���õ����ݱ��Ϊ"+bill.getAllId());
            	return true;
            } else return false;
	    }catch(RemoteException e){
	        e.printStackTrace();
	        return false;
	    }
	}
	
	@Override
	public boolean copyBill(BillVO bill){
	    if(bill instanceof CashCostBillVO){
	        CashCostBillVO oldOne = (CashCostBillVO) bill;
	        CashCostBillVO copy = new CashCostBillVO(
	            Timetools.getDate(), Timetools.getTime(), this.getNewId(), 
	            oldOne.getOperator(), BillVO.PASS, oldOne.getAccountId(), oldOne.getTableModel()
	        );
	        return saveBill(copy, "��岢�����ֽ���õ�", "��岢���ƺ��µ��ֽ���õ����ݱ��Ϊ"+copy.getAllId());
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
            billPO.setState(3);
            billVO.setState(3);
            return saveBill(billVO, "����ֽ���õ�", "ͨ����˵��ֽ���õ����ݱ��Ϊ"+billId);
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
            return saveBill(billVO, "����ֽ���õ�", "���ݱ��Ϊ"+billId+"���ֽ���õ����δͨ��");
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
