package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import blservice.billblservice.BillExamineService;
import blservice.billblservice.BillOperationService;
import blservice.billblservice.SalesBillBLService;
import blservice.infoservice.GetCustomerInterface;
import businesslogic.inter.AddLogInterface;
import dataservice.SalesBillDataService;
import ds_stub.SalesBillDs_stub;
import po.billpo.BillPO;
import po.billpo.SalesBillPO;
import po.billpo.SalesItemsPO;
import presentation.component.MyTableModel;
import presentation.tools.Timetools;
import rmi.Rmi;
import vo.PromotionVO;
import vo.billvo.BillVO;
import vo.billvo.SalesBillVO;

/**
 * @author �Ҷ��
 */
public class SalesBillBL implements SalesBillBLService, BillOperationService, BillExamineService {
    
    private SalesBillDataService salesBillDs = Rmi.flag ? Rmi.getRemote(SalesBillDataService.class) : new SalesBillDs_stub();
    private GetCustomerInterface customerInfo = new CustomerBL();
    private AddLogInterface addLog = new LogBL();

    @Override
    public String getNewId() {
        try{
            return "XSD-" + Timetools.getDate() + "-" + salesBillDs.getNewId();
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteBill(String id) {
        try{
            SalesBillPO bill = salesBillDs.getBillById(id);
            if(bill.getState() == BillPO.PASS) return false;
            if (salesBillDs.deleteBill(id)) {
            	addLog.add("ɾ�����۵�", "ɾ�������۵����ݱ��Ϊ"+id);
            	return true;
            } else return false;
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean saveBill(SalesBillVO bill) {
		return saveBill(bill, "�������۵�", "��������۵����ݱ��Ϊ"+bill.getAllId());
	}
	
	private boolean saveBill(SalesBillVO bill, String operation, String detail) {
		try{
            if (salesBillDs.saveBill(toPO(bill))) {
            	addLog.add(operation, detail);
            	return true;
            } else return false;
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public MyTableModel getFinishedBills(){
        return search("��״̬����", BillVO.PASS + "");
    }
    
    public MyTableModel getFinishedBills(String customerId){
        try{
            String field = "CONCAT(SBCondition,',',SBCustomerID)";
            String key = BillPO.PASS + "," + customerId;
            ArrayList<SalesBillPO> bills = salesBillDs.getBillsBy(field, key, true);
            return toModel(bills);
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public MyTableModel search(String type, String key) {
        try{
            String field = null;
            if("���������".equals(type)){
                field = "SBID";
            } // other searching methods not yet considered
            ArrayList<SalesBillPO> bills = salesBillDs.getBillsBy(field, key, true);
            return toModel(bills);
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public MyTableModel getBillsByDate(String from, String to){
        try{
            ArrayList<SalesBillPO> bills = salesBillDs.getBillByDate(from, to);
            return toModel(bills);
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }
    
    public ArrayList<SalesBillPO> getBillPOsByDate(String from, String to){
        try{
            return salesBillDs.getBillByDate(from, to);
        }catch(RemoteException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public boolean offsetBill(String id){
        try{
            SalesBillPO bill = salesBillDs.getBillById(id);
            ArrayList<SalesItemsPO> items = new ArrayList<>();
            bill.getSalesBillItems().forEach(i -> items.add(new SalesItemsPO(
                i.getComId(), i.getComRemark(), -i.getComQuantity(), i.getComPrice(), -i.getComSum()
            )));
            SalesBillPO offset = new SalesBillPO(
                Timetools.getDate(), Timetools.getTime(), this.getNewId(), bill.getOperator(), BillPO.PASS,
                bill.getCustomerId(), bill.getSalesManName(), bill.getRemark(), bill.getPromotionId(),
                -bill.getBeforeDiscount(), -bill.getDiscount(), -bill.getCoupon(), -bill.getAfterDiscount(), items);
            if (salesBillDs.saveBill(offset)) {
            	addLog.add("������۵�", "���������۵����ݱ��Ϊ"+bill.getAllId());
            	return true;
            } else return false;
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean copyBill(BillVO bill){
        if(bill instanceof SalesBillVO){
            SalesBillVO old = (SalesBillVO) bill;
            SalesBillVO copy = new SalesBillVO(
                Timetools.getDate(), Timetools.getTime(), this.getNewId(), old.getOperator(),
                BillVO.PASS, old.getCustomerId(), old.getModel(),
                old.getRemark(), old.getBeforeDiscount(), old.getDiscount(), old.getCoupon(), old.getSum()
            );
            return saveBill(copy, "��岢�������۵�", "��岢���ƺ��µ����۵����ݱ��Ϊ"+copy.getAllId());
        }
        return false;
    }

    private MyTableModel toModel(ArrayList<SalesBillPO> bills){
        String[] columnNames = {"�ƶ�ʱ��", "���ݱ��"};
        String[][] data = new String[bills.size()][columnNames.length];
        for(int i = 0; i < bills.size(); i++){
            SalesBillPO salesBill = bills.get(i);
            data[i][0] = salesBill.getDate() + " " + salesBill.getTime();
            data[i][1] = "XSD-" + salesBill.getDate() + "-" + salesBill.getId();
        }
        return new MyTableModel(data, columnNames);
    }

    private SalesBillPO toPO(SalesBillVO bill){
        MyTableModel model = bill.getModel();
        ArrayList<SalesItemsPO> items = new ArrayList<>();
        for(int i = 0; i < model.getRowCount(); i++){
            String[] row = model.getValueAtRow(i);
            int num = Integer.parseInt(row[5]);
            double price = Double.parseDouble(row[4]),
                   sum = Double.parseDouble(row[6]);
            items.add(new SalesItemsPO(row[0], row[7], num, price, sum));
        }
        // TODO promotionId not considered here
        return new SalesBillPO(bill.getDate(), bill.getTime()
            , bill.getId(), bill.getOperator(), bill.getState()
            , bill.getCustomerId(), customerInfo.getCustomer(bill.getCustomerId()).getSalesman(), bill.getRemark(), "", bill.getBeforeDiscount()
            , bill.getDiscount(), bill.getCoupon(), bill.getSum()
            , items);
    }
    
	@Override
	public boolean examineBill(String billId) {
        try{
            SalesBillVO billVO = BillTools.toSalesBillVO(salesBillDs.getBillById(billId));
            billVO.setState(3);
            return saveBill(billVO, "������۵�", "ͨ����˵����۵����ݱ��Ϊ"+billId);
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public boolean notPassBill(String billId) {
        try{
        	SalesBillVO billVO = BillTools.toSalesBillVO(salesBillDs.getBillById(billId));
            billVO.setState(4);
            return saveBill(billVO, "������۵�", "���ݱ��Ϊ"+billId+"�����۵����δͨ��");
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public BillVO getBillById(String billId) {
		try {
			return BillTools.toSalesBillVO(salesBillDs.getBillById(billId));
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}

    @Override
    public PromotionVO getBestPromotion(int userType, MyTableModel goods, double sum) {
        // TODO Auto-generated method stub
        return null;
    }
}
