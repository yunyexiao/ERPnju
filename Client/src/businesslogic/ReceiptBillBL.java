package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import blservice.billblservice.BillExamineService;
import blservice.billblservice.BillOperationService;
import blservice.billblservice.ReceiptBillBLService;
import businesslogic.inter.AddLogInterface;
import dataservice.ReceiptBillDataService;
import ds_stub.ReceiptBillDs_stub;
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
            	addLog.add("ɾ���տ", "ɾ�����տ���ݱ��Ϊ"+id);
            	return true;
            } else return false;
		}catch(RemoteException e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean saveBill(ReceiptBillVO bill) {
		return saveBill(bill, "�����տ", "������տ���ݱ��Ϊ"+bill.getAllId());
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
            	addLog.add("����տ", "�������տ���ݱ��Ϊ"+bill.getAllId());
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
	        return saveBill(copy, "��岢�����տ", "��岢���ƺ��µ��տ���ݱ��Ϊ"+copy.getAllId());
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
            billPO.setState(3);
            billVO.setState(3);
            return saveBill(billVO, "����տ", "ͨ����˵��տ���ݱ��Ϊ"+billId);
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
            return saveBill(billVO, "����տ", "���ݱ��Ϊ"+billId+"���տ���δͨ��");
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
