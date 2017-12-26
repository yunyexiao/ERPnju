package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import blservice.billblservice.BillExamineService;
import blservice.billblservice.BillOperationService;
import blservice.billblservice.SalesReturnBillBLService;
import blservice.infoservice.GetCustomerInterface;
import businesslogic.inter.AddLogInterface;
import dataservice.SalesReturnBillDataService;
import ds_stub.SalesReturnBillDs_stub;
import po.billpo.BillPO;
import po.billpo.SalesItemsPO;
import po.billpo.SalesReturnBillPO;
import presentation.component.MyTableModel;
import presentation.tools.Timetools;
import rmi.Rmi;
import vo.billvo.BillVO;
import vo.billvo.SalesReturnBillVO;


public class SalesReturnBillBL implements SalesReturnBillBLService, BillOperationService, BillExamineService{
    
    private SalesReturnBillDataService salesReturnBillDs = Rmi.flag ? Rmi.getRemote(SalesReturnBillDataService.class) : new SalesReturnBillDs_stub();
    private GetCustomerInterface customerInfo = new CustomerBL();
    private AddLogInterface addLog = new LogBL();
    @Override
    public String getNewId() {
        try{
            return "XSTHD-" + Timetools.getDate() + "-" + salesReturnBillDs.getNewId();
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteBill(String id) {
        try{
            SalesReturnBillPO bill = salesReturnBillDs.getBillById(id);
            if(bill.getState() == BillPO.PASS) return false;
            int length = id.length();
            if (salesReturnBillDs.deleteBill(id.substring(length - 5, length))) {
            	addLog.add("删除销售退货单", "删除的销售退货单单据编号为"+id);
            	return true;
            } else return false;
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
	public boolean saveBill(SalesReturnBillVO bill) {
		return saveBill(bill, "保存销售退货单", "保存的销售退货单单据编号为"+bill.getAllId());
	}
	
	private boolean saveBill(SalesReturnBillVO bill, String operation, String detail) {
		try{
            if (salesReturnBillDs.saveBill(toPO(bill))) {
            	addLog.add(operation, detail);
            	return true;
            } else return false;
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }
    
    public ArrayList<SalesReturnBillPO> getBillPOsByDate(String from, String to){
        try{
            return salesReturnBillDs.getBillsByDate(from, to);
        }catch(RemoteException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public boolean offsetBill(String id){
        try{
            SalesReturnBillPO bill = salesReturnBillDs.getBillById(id);
            ArrayList<SalesItemsPO> items = new ArrayList<>();
            bill.getSalesReturnBillItems().forEach(i -> items.add(new SalesItemsPO(
                i.getComId(), i.getComRemark(), -i.getComQuantity(), i.getComPrice(), -i.getComSum()
            )));
            SalesReturnBillPO offset = new SalesReturnBillPO(
                Timetools.getDate(), Timetools.getTime(), this.getNewId(), bill.getOperator(), BillPO.PASS,
                bill.getCustomerId(), bill.getSalesManName(), bill.getRemark(), bill.getOriginalSBId(), 
                -bill.getOriginalSum(), -bill.getReturnSum(), items);
            if (salesReturnBillDs.saveBill(offset)) {
            	addLog.add("红冲销售退货单", "被红冲的销售退货单单据编号为"+bill.getAllId());
            	return true;
            } else return false;
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean copyBill(BillVO bill){
        if(bill instanceof SalesReturnBillVO){
            SalesReturnBillVO old = (SalesReturnBillVO) bill;
            SalesReturnBillVO copy = new SalesReturnBillVO(
                Timetools.getDate(), Timetools.getTime(), this.getNewId(), old.getOperator(),
                BillVO.PASS, old.getCustomerId(), old.getModel(), old.getRemark(), 
                old.getOriginalSBId(), old.getDiscountRate(), old.getOriginalSum(), old.getSum()
            );
            return saveBill(copy, "红冲并复制销售退货单", "红冲并复制后新的销售退货单单据编号为"+copy.getAllId());
        }
        return false;
    }

    private SalesReturnBillPO toPO(SalesReturnBillVO bill){
        String date = bill.getDate(),
               time = bill.getTime(),
               id = bill.getId(),
               operatorId = bill.getOperator(),
               customerId = bill.getCustomerId(),
               salesManName = customerInfo.getCustomer(bill.getCustomerId()).getSalesman(),
               remark = bill.getRemark(),
               originalSBId = bill.getOriginalSBId();
        int state = bill.getState();
        double originalSum = bill.getOriginalSum(),
               returnSum = bill.getSum();
        ArrayList<SalesItemsPO> items = new ArrayList<>();
        MyTableModel model = bill.getModel();
        for(int i = 0; i < model.getRowCount(); i++){
            items.add(getItem(model.getValueAtRow(i)));
        }
        return new SalesReturnBillPO(date, time, id, operatorId
            , state, customerId, salesManName, remark, originalSBId
            , originalSum, returnSum, items);
    }
    
    

    private SalesItemsPO getItem(String[] data){
        String id = data[0], remark = data[7];
        int num = Integer.parseInt(data[5]);
        double price = Double.parseDouble(data[4]),
               sum = Double.parseDouble(data[6]);
        return new SalesItemsPO(
            id, remark, num, price, sum);
    }

	@Override
	public boolean examineBill(String billId) {
        try{
            SalesReturnBillVO billVO = BillTools.toSalesReturnBillVO(salesReturnBillDs.getBillById(billId));
            billVO.setState(3);
            return saveBill(billVO, "审核销售退货单", "通过审核的销售退货单单据编号为"+billId);
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public boolean notPassBill(String billId) {
        try{
        	SalesReturnBillVO billVO = BillTools.toSalesReturnBillVO(salesReturnBillDs.getBillById(billId));
            billVO.setState(4);
            return saveBill(billVO, "审核销售退货单", "单据编号为"+billId+"的销售退货单审核未通过");
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public BillVO getBillById(String billId) {
		try {
			return BillTools.toSalesReturnBillVO(salesReturnBillDs.getBillById(billId));
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}
}
