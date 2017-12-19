package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;

import bl_stub.CustomerBL_stub;
import blservice.billblservice.BillOperationService;
import blservice.billblservice.SalesBillBLService;
import dataservice.SalesBillDataService;
import ds_stub.SalesBillDs_stub;
import po.billpo.BillPO;
import po.billpo.SalesBillPO;
import po.billpo.SalesItemsPO;
import presentation.component.MyTableModel;
import presentation.tools.Timetools;
import vo.CommodityVO;
import vo.billvo.BillVO;
import vo.billvo.SalesBillVO;

/**
 * @author �Ҷ��
 */
public class SalesBillBL implements SalesBillBLService, BillOperationService {
    
    private SalesBillDataService salesBillDs;
    
    public SalesBillBL(){
        salesBillDs = new SalesBillDs_stub();
    }

    @Override
    public String getNewId() {
        try{
            Calendar c = Calendar.getInstance();
            String date = c.get(Calendar.YEAR) + ""
                        + c.get(Calendar.MONTH) + ""
                        + c.get(Calendar.DATE);
            return "XSD-" + date + "-" + salesBillDs.getNewId();
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteBill(String id) {
        try{
            // passed bills cannot be deleted, only can be offsetted
            SalesBillPO bill = salesBillDs.getBillById(id);
            if(bill.getState() == BillPO.PASS) return false;

            int length = id.length();
            return salesBillDs.deleteBill(id.substring(length - 5, length));
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean saveBill(SalesBillVO bill) {
        try{
            return salesBillDs.saveBill(toPO(bill));
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateBill(SalesBillVO bill) {
        try{
            return salesBillDs.saveBill(toPO(bill));
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public SalesBillVO getBill(String id) {
        try{
            return toVO(salesBillDs.getBillById(id));
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
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
            return salesBillDs.saveBill(new SalesBillPO(
                Timetools.getDate(), Timetools.getTime(), this.getNewId(), bill.getOperator(), BillPO.PASS,
                bill.getCustomerId(), bill.getSalesManName(), bill.getRemark(), bill.getPromotionId(),
                -bill.getBeforeDiscount(), -bill.getDiscount(), -bill.getCoupon(), -bill.getAfterDiscount(), items
            ));
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
                BillVO.PASS, old.getCustomerId(), old.getCustomerName(), old.getModel(),
                old.getRemark(), old.getBeforeDiscount(), old.getDiscount(), old.getCoupon(), old.getSum()
            );
            return saveBill(copy);
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
            , bill.getCustomerId(), bill.getCustomerName()
            , bill.getRemark(), "", bill.getBeforeDiscount()
            , bill.getDiscount(), bill.getCoupon(), bill.getSum()
            , items);
    }
    
    private SalesBillVO toVO(SalesBillPO bill){
        String[] columnNames = {"���", "����", "�ͺ�", "���", "����", "����", "�ܼ�", "��ע"};
        ArrayList<SalesItemsPO> items = bill.getSalesBillItems();
        String[][] data;
        if(items == null){
            data = null;
        }else{
            data = new String[items.size()][];
            for(int i = 0; i < data.length; i++){
                data[i] = getRow(items.get(i));
            }
        }
        MyTableModel model = new MyTableModel(data, columnNames);
        String date = bill.getDate(),
               time = bill.getTime(),
               id = bill.getId(),
               operatorId = bill.getOperator(),
               cusId = bill.getCustomerId(),
               cusName = new CustomerBL_stub().getCustomer(cusId)
                           .getName(),
               remark = bill.getRemark();
        int state = bill.getState();
        double sum = bill.getAfterDiscount(),
               beforeDiscount = bill.getBeforeDiscount(),
               discount = bill.getDiscount(),
               coupon = bill.getCoupon();
        return new SalesBillVO(date, time, id, operatorId, state
            , cusId, cusName, model, remark, beforeDiscount
            , discount, coupon, sum);
    }
    
    private String[] getRow(SalesItemsPO item){
        String id = item.getComId();
        CommodityVO c = new CommodityBL().getCommodity(id);
        String name = c.getName(),
               type = c.getType(),
               store = c.getStore(),
               price = item.getComPrice() + "",
               num = item.getComQuantity() + "",
               sum = item.getComSum() + "",
               remark = item.getComRemark();
        return new String[]{
                id, name, type, store, price, num, sum, remark
        };
    }

}
