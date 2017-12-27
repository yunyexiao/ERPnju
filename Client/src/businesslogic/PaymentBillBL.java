package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import blservice.billblservice.BillExamineService;
import blservice.billblservice.BillOperationService;
import blservice.billblservice.PaymentBillBLService;
import businesslogic.inter.AddLogInterface;
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

	private PaymentBillDataService paymentBillDataService = Rmi.flag ? Rmi.getRemote(PaymentBillDataService.class) : new PaymentBillDs_stub();
	private AddLogInterface addLog = new LogBL();

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
            if (paymentBillDataService.deleteBill(id)) {
            	addLog.add("ɾ�����", "ɾ���ĸ�����ݱ��Ϊ"+id);
            	return true;
            } else return false;
		}catch(RemoteException e){
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public boolean saveBill(PaymentBillVO bill) {
		return saveBill(bill, "���渶�", "����ĸ�����ݱ��Ϊ"+bill.getAllId());
	}
	private boolean saveBill(PaymentBillVO bill, String operation, String detail) {
		try{
            if (paymentBillDataService.saveBill(toPO(bill))) {
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
	        PaymentBillPO bill = paymentBillDataService.getBillById(id);
	        ArrayList<TransferItem> items = new ArrayList<>();
	        bill.getTransferList().forEach(i -> items.add(
	            new TransferItem(i.getAccountId(), -i.getMoney(), i.getRemark())));
	        PaymentBillPO offset = new PaymentBillPO(
	            Timetools.getDate(), Timetools.getTime(), this.getNewId(), bill.getOperator()
	            , BillPO.PASS, bill.getCustomerId(), items, -bill.getSum());
	        if (paymentBillDataService.saveBill(offset)) {
            	addLog.add("��帶�", "�����ĸ�����ݱ��Ϊ"+bill.getAllId());
            	return true;
            } else return false;
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
	            old.getOperator(), BillVO.PASS, old.getCustomerId(),old.getTableModel());
	        return saveBill(copy, "��岢���Ƹ��", "��岢���ƺ��µĸ�����ݱ��Ϊ"+copy.getAllId());
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
            return saveBill(billVO, "��˸��", "ͨ����˵ĸ�����ݱ��Ϊ"+billId);
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
            return saveBill(billVO, "��˸��", "���ݱ��Ϊ"+billId+"�ĸ�����δͨ��");
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
