package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import blservice.billblservice.BillExamineService;
import blservice.billblservice.BillOperationService;
import blservice.billblservice.PurchaseReturnBillBLService;
import businesslogic.inter.AddLogInterface;
import dataservice.PurchaseReturnBillDataService;
import ds_stub.PurchaseReturnBillDs_stub;
import po.billpo.BillPO;
import po.billpo.PurchaseReturnBillPO;
import po.billpo.SalesItemsPO;
import presentation.tools.Timetools;
import rmi.Rmi;
import vo.billvo.BillVO;
import vo.billvo.PurchaseReturnBillVO;


public class PurchaseReturnBillBL implements PurchaseReturnBillBLService, BillOperationService, BillExamineService {
    
    private PurchaseReturnBillDataService purchaseReturnBillDs = Rmi.flag ? Rmi.getRemote(PurchaseReturnBillDataService.class) : new PurchaseReturnBillDs_stub();
    private AddLogInterface addLog = new LogBL();

    @Override
    public String getNewId() {
        try{
            return "JHTHD-" + Timetools.getDate() + "-" + purchaseReturnBillDs.getNewId();
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteBill(String id) {
        try{
            PurchaseReturnBillPO bill = purchaseReturnBillDs.getBillById(id);
            if(bill.getState() == BillPO.PASS) return false;
            if (purchaseReturnBillDs.deleteBill(id)) {
            	addLog.add("ɾ�������˻���", "ɾ���Ľ����˻������ݱ��Ϊ"+id);
            	return true;
            } else return false;
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }
    @Override
	public boolean saveBill(PurchaseReturnBillVO bill) {
		return saveBill(bill, "��������˻���", "����Ľ����˻������ݱ��Ϊ"+bill.getAllId());
	}
    private boolean saveBill(PurchaseReturnBillVO bill, String operation, String detail) {
    	try{
            if (purchaseReturnBillDs.saveBill(toPO(bill))) {
            	addLog.add(operation, detail);
            	return true;
            } else return false;
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }
    
    public ArrayList<PurchaseReturnBillPO> getPurchaseReturnBillPOsByDate(String from, String to){
        try{
            return purchaseReturnBillDs.getBillsByDate(from, to);
        }catch(RemoteException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public boolean offsetBill(String id){
        try{
            PurchaseReturnBillPO bill = purchaseReturnBillDs.getBillById(id);
            ArrayList<SalesItemsPO> items = new ArrayList<>();
            bill.getPurchaseReturnBillItems().forEach(i -> items.add(new SalesItemsPO(
                i.getComId(), i.getComRemark(), -i.getComQuantity(), i.getComPrice(), -i.getComSum()
            )));
            PurchaseReturnBillPO offset = new PurchaseReturnBillPO(
                Timetools.getDate(), Timetools.getTime(), this.getNewId(), bill.getOperator(), BillPO.PASS, 
                bill.getSupplierId(), bill.getRemark(), -bill.getSum(), items);
            if (purchaseReturnBillDs.saveBill(offset)) {
            	addLog.add("�������˻���", "�����Ľ����˻������ݱ��Ϊ"+bill.getAllId());
            	return true;
            } else return false;
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean copyBill(BillVO bill){
        if(bill instanceof PurchaseReturnBillVO){
            PurchaseReturnBillVO old = (PurchaseReturnBillVO) bill;
            PurchaseReturnBillVO copy = new PurchaseReturnBillVO(
                Timetools.getDate(), Timetools.getTime(), this.getNewId(), old.getOperator(),
                BillVO.PASS, old.getCustomerId(), old.getModel(), old.getRemark(), old.getSum()
            );
            return saveBill(copy, "��岢���ƽ����˻���", "��岢���ƺ��µĽ����˻������Ϊ"+copy.getAllId());
        }
        return false;
    }

    private PurchaseReturnBillPO toPO(PurchaseReturnBillVO bill){
        ArrayList<SalesItemsPO> items = new ArrayList<>();
        for(int i = 0; i < bill.getModel().getRowCount(); i++){
            String[] row = bill.getModel().getValueAtRow(i);
            int num = Integer.parseInt(row[5]);
            double price = Double.parseDouble(row[4]),
                   sum = Double.parseDouble(row[6]);
            items.add(new SalesItemsPO(
                row[0], row[7], num, price, sum));
        }
        return new PurchaseReturnBillPO(bill.getDate(), bill.getTime()
            , bill.getId(), bill.getOperator(), bill.getState()
            , bill.getCustomerId(), bill.getRemark(), bill.getSum(), items);
    }

	@Override
	public boolean examineBill(String billId) {
        try{
            PurchaseReturnBillVO billVO = BillTools.toPurchaseReturnBillVO(purchaseReturnBillDs.getBillById(billId));
            billVO.setState(3);
            return saveBill(billVO, "��˽����˻���", "ͨ����˵Ľ����˻������ݱ��Ϊ"+billId);
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public boolean notPassBill(String billId) {
        try{
        	PurchaseReturnBillVO billVO = BillTools.toPurchaseReturnBillVO(purchaseReturnBillDs.getBillById(billId));
            billVO.setState(4);
            return saveBill(billVO, "��˽����˻���", "���ݱ��Ϊ"+billId+"�Ľ����˻������δͨ��");
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public BillVO getBillById(String billId) {
		try {
			return BillTools.toPurchaseReturnBillVO(purchaseReturnBillDs.getBillById(billId));
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}
}
